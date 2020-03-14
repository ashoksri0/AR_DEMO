package com.p3.poc.demo.ar.arsummary;

import com.p3.poc.demo.ar.invoice_details.model.ARSummary;
import com.p3.poc.demo.ar.ledger.entity.Ledger;
import com.p3.poc.demo.ar.ledger.enums.TransactionMode;
import com.p3.poc.demo.ar.ledger.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.p3.poc.demo.ar.ledger.enums.TransactionMode.AMOUNT_RECEIVABLE;
import static com.p3.poc.demo.ar.ledger.enums.TransactionMode.AMOUNT_RECEIVED;

/**
 * Created by Ashok Kumar N
 * on 09/03/20.
 */

@Service
public class ARService {
    @Autowired
    LedgerRepository ledgerRepository;

    public AR360Model getAr360ViewLIst(@RequestParam final Long userID, @RequestParam(required = false) final Long invoiceID,
                                       @RequestParam(required =
                                               false, defaultValue = "1970-01-01") final Date startDate, @RequestParam(required = false, defaultValue = "2050-01-01") final Date endDate) {

        List<Ledger> ledgerList;
        List<DataSetBean> amount_received_list = new ArrayList();
        List<DataSetBean> amount_receivable_list = new ArrayList();

        if (invoiceID == null) {
            ledgerList =
                    ledgerRepository.findAllByUsers_IdAndTranscationDateBetweenOrderByTranscationDateDesc(userID, startDate, endDate);
        } else {
            ledgerList = ledgerRepository.findAllByUsers_IdAndInvoice_IdAndTranscationDateBetweenOrderByTranscationDateDesc(userID, invoiceID,
                    startDate, endDate);
        }
        List<LedgerModel> ledgerModelList = new ArrayList();
        ARSummary arSummary = ARSummary.builder().openingBalance(0D).totalDue(0D).totalReceivable(0D).totalReceived(0D).build();
        ledgerList.forEach(ledger -> {
            LedgerModel ledgerModel = LedgerModel.builder().date(ledger.getTranscationDate()).invoice(ledger.getInvoice().getId()).build();
            TransactionMode transactionMode = ledger.getTransactionMode();
            switch (transactionMode) {
                case AMOUNT_RECEIVED:
                    amount_received_list.add(0, DataSetBean.builder().x(ledger.getTranscationDate()).y(ledger.getTranscation()).build());
                    ledgerModel.setPaymentMode(AMOUNT_RECEIVED.toString());
                    arSummary.setTotalReceived(ledger.getTranscation() + arSummary.getTotalReceived());
                    break;
                case AMOUNT_RECEIVABLE:
                    amount_receivable_list.add(0, DataSetBean.builder().x(ledger.getTranscationDate()).y(ledger.getTranscation()).build());
                    ledgerModel.setPaymentMode(AMOUNT_RECEIVABLE.toString());
                    arSummary.setTotalReceivable(ledger.getTranscation() + arSummary.getTotalReceivable());
                    break;
            }
            ledgerModel.setAmount(ledger.getTranscation());
            ledgerModel.setBalanceReceivable(invoiceID == null ? ledger.getUserBalance() : ledger.getInvoiceBalance());
            ledgerModelList.add(ledgerModel);
        });
        if (ledgerList.size() > 0) {
            Double openingBalance = invoiceID == null ? ledgerList.get(ledgerList.size() - 1).getUserOpeningBalance() :
                    ledgerList.get(ledgerList.size() - 1).getInvoiceOpeningBalance();
            arSummary.setOpeningBalance(openingBalance);
            arSummary.setTotalReceivable(arSummary.getTotalReceivable() + openingBalance);
        }
        arSummary.setTotalDue(arSummary.getTotalReceivable() - arSummary.getTotalReceived());


        // cumulative function for amount_received_list
        for (int i = 0; i < amount_received_list.size() - 1; i++) {
            amount_received_list.get(i + 1).setY(amount_received_list.get(i + 1).getY() + amount_received_list.get(i).getY());
        }

        // cumulative function for amount_receivable_list
        for (int i = 0; i < amount_receivable_list.size() - 1; i++) {
            amount_receivable_list.get(i + 1).setY(amount_receivable_list.get(i + 1).getY() + amount_receivable_list.get(i).getY());
        }

        // set first and last data value for amount_receivable_list
        if (amount_receivable_list.size() == 0) {
            amount_receivable_list.add(DataSetBean.builder().x(startDate).y(arSummary.getOpeningBalance()).build());
            amount_receivable_list.add(DataSetBean.builder().x(min(new Date(new java.util.Date().getTime()), endDate)).y(arSummary.getOpeningBalance()).build());
        }

        // set last data value for amount_received_list
        if (amount_received_list.size() == 0) {
            amount_received_list.add(DataSetBean.builder().x(startDate).y(0D).build());
            amount_received_list.add(DataSetBean.builder().x(min(new Date(new java.util.Date().getTime()), endDate)).y(0D).build());
        }

        // add extra Points to match the graph to end data
        DataSetBean last_receivable = amount_receivable_list.get(amount_receivable_list.size() - 1);
        DataSetBean last_received = amount_received_list.get(amount_received_list.size() - 1);
        addExtraEntry(last_receivable, last_received, amount_receivable_list, amount_received_list);

        // uncomment below if you want dataset at same period for both amount_receivable_list and amount_received_list
        dataPointsAdapter(amount_receivable_list, amount_received_list);
        //System.out.println(amount_receivable_list);
        //System.out.println(amount_received_list);

        System.out.println(amount_receivable_list);
        System.out.println(amount_received_list);

        return AR360Model.builder()
                .ledgerModelList(ledgerModelList)
                .amount_receivable_list(amount_receivable_list)
                .amount_received_list(amount_received_list)
                .arSummary(arSummary)
                .build();
    }

    private void dataPointsAdapter(List<DataSetBean> amount_receivable_list, List<DataSetBean> amount_received_list) {
        int currentReceivablePosition = 0;
        int currentReceivedPosition = 0;
        java.util.Date checkingReceivableDate = amount_receivable_list.get(currentReceivablePosition).getX();
        java.util.Date checkingReceivedDate = amount_received_list.get(currentReceivedPosition).getX();

        boolean endNotReached = true;
        do {
            if (checkingReceivableDate.equals(checkingReceivedDate)) {
                currentReceivablePosition++;
                currentReceivedPosition++;
                if (currentReceivablePosition >= amount_receivable_list.size() && currentReceivedPosition >= amount_received_list.size()) {
                    endNotReached = false;
                    continue;
                }
                checkingReceivableDate = amount_receivable_list.get(currentReceivablePosition).getX();
                checkingReceivedDate = amount_received_list.get(currentReceivedPosition).getX();
            } else if (checkingReceivableDate.before((checkingReceivedDate)) && currentReceivedPosition == 0) {
                amount_received_list.add(currentReceivedPosition, new DataSetBean(checkingReceivableDate, 0D));
                currentReceivablePosition++;
                currentReceivedPosition++;
                checkingReceivableDate = amount_receivable_list.get(currentReceivablePosition).getX();
                checkingReceivedDate = amount_received_list.get(currentReceivedPosition).getX();
            } else if (checkingReceivableDate.before((checkingReceivedDate))) {
                amount_received_list.add(currentReceivedPosition, new DataSetBean(checkingReceivableDate, amount_received_list.get(currentReceivedPosition - 1).getY()));
                currentReceivablePosition++;
                currentReceivedPosition++;
                checkingReceivableDate = amount_receivable_list.get(currentReceivablePosition).getX();
                checkingReceivedDate = amount_received_list.get(currentReceivedPosition).getX();
            } else if (checkingReceivedDate.before((checkingReceivableDate)) && currentReceivedPosition == 0) {
                amount_receivable_list.add(currentReceivablePosition, new DataSetBean(checkingReceivedDate, 0D));
                currentReceivablePosition++;
                currentReceivedPosition++;
                checkingReceivableDate = amount_receivable_list.get(currentReceivablePosition).getX();
                checkingReceivedDate = amount_received_list.get(currentReceivedPosition).getX();
            } else if (checkingReceivedDate.before((checkingReceivableDate))) {
                amount_receivable_list.add(currentReceivablePosition, new DataSetBean(checkingReceivedDate, amount_receivable_list.get(currentReceivablePosition - 1).getY()));
                currentReceivablePosition++;
                currentReceivedPosition++;
                checkingReceivableDate = amount_receivable_list.get(currentReceivablePosition).getX();
                checkingReceivedDate = amount_received_list.get(currentReceivedPosition).getX();
            }
        } while (endNotReached);
    }

    private void addExtraEntry(DataSetBean last_receivable, DataSetBean
            last_received, List<DataSetBean> amount_receivable_list, List<DataSetBean> amount_received_list) {
        if (last_receivable.getX().before(last_received.getX())) {
            amount_receivable_list.add(DataSetBean.builder().x(last_received.getX()).y(last_receivable.getY()).build());
        } else {
            amount_received_list.add(DataSetBean.builder().x(last_receivable.getX()).y(last_received.getY()).build());
        }
    }

    private java.util.Date min(Date date, Date endDate) {
        if (endDate.before(date)) {
            return endDate;
        }
        return date;
    }
}
