package com.qashqade.documentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "transactions")
public class Transaction {

    @Id
    private Long id;
    @Field(value = "trx")
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
