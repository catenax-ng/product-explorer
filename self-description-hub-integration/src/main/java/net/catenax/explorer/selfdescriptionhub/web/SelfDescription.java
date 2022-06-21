package net.catenax.explorer.selfdescriptionhub.web;

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
