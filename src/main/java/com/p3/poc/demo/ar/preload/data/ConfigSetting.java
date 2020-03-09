package com.p3.poc.demo.ar.preload.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Created by Ashok Kumar N
 * on 09/03/20.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class ConfigSetting {
private Integer userCount;
private Integer invoiceCount;
private Integer orderCount;
}
