package com.p3.poc.demo.ar.preload.data.service;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashok Kumar N
 * on 09/03/20.
 */

@Service
@Data
public class PreLoadService {
    private Map<String,Double> productsMap =new HashMap();
    public void getOrder()
    {
       productsMap.put("NV1500", 21913.72);
       productsMap.put("Altima", 29493.22);
       productsMap.put("F-Series", 12213.48);
       productsMap.put("Evora", 11320.89);
       productsMap.put("STS-V", 37701.41);
       productsMap.put("Tracer", 39420.72);
       productsMap.put("XG350", 35765.60);
       productsMap.put("Flex", 24843.65);
       productsMap.put("Protege", 29999.09);
       productsMap.put("Golf", 24746.17);
       productsMap.put("Cobalt SS", 34339.04);
       productsMap.put("B-Series", 27570.78);
       productsMap.put("Econoline E350", 20205.53);
       productsMap.put("G35", 32396.10);
       productsMap.put("Sable", 11396.35);
       productsMap.put("Grand Prix Turbo", 30893.97);
       productsMap.put("Tiburon", 14884.77);
       productsMap.put("B-Class", 17637.58);
       productsMap.put("JX", 37717.25);
       productsMap.put("Town & Country", 33639.05);
       productsMap.put("Cougar", 12825.76);
       productsMap.put("C-Class", 27660.44);
       productsMap.put("Aerio", 37327.37);
       productsMap.put("Five Hundred", 25078.54);
       productsMap.put("CTS", 25947.32);
       productsMap.put("XJ Series", 30938.98);
       productsMap.put("Avalanche", 23390.38);
       productsMap.put("Pathfinder", 36835.61);
       productsMap.put("Grand Prix", 32083.63);
       productsMap.put("Ram 2500 Club", 21517.44);

    }

}
