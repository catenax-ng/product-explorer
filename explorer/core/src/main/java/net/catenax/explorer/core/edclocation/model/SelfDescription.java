package net.catenax.explorer.core.edclocation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelfDescription {

  String companyNumber;

  String headquarterCountry;

  String legalCountry;

  String bpn;

  String serviceProvider;

  String type;
}
