package com.qashqade.documentservice.model;

import lombok.Data;

@Data
public class Message {
    private Long id;
    String trx;
    String subTransactions;
    String appliedDateChoice;
    boolean isRealized;
    String fxRateSource;
    String targetCcy;
    String fxTable;
    String fx;
    String deals;
    String dealGroups;
    String vintageYearNames;
    String lps;
    String lpgroups;
    String splitTypes;
    String allDeals;
    String amount;
    String originalAmount;
    String appliedDate;
    String allLps;
    String impactName;
    String uid;
    String ccy;
    String gpid;
    String gahid;
    String dealAllocationRule;
    String lpAllocationRule;
    String customfields;
    boolean needsToSetProgressiveValues;
    boolean isPartOfAllProgressiveRuns;
}
