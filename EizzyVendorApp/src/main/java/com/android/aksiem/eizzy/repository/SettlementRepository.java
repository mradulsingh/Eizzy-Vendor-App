package com.android.aksiem.eizzy.repository;

import com.android.aksiem.eizzy.di.AppScope;
import com.android.aksiem.eizzy.vo.settlement.SettlementItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


@AppScope
public class SettlementRepository {


    public SettlementRepository() {

    }


    public List<SettlementItem> getSettlementList() {

        List<SettlementItem> settlementList = new ArrayList<>();
        settlementList.add(new SettlementItem("DFHESRTER654756",null,0,null,"",null));
        settlementList.add(new SettlementItem("DFHESRTER654757",null,0,null,"",null));
        settlementList.add(new SettlementItem("DFHESRTER654758",null,0,null,"",null));
        settlementList.add(new SettlementItem("DFHESRTER654759",null,0,null,"",null));
        settlementList.add(new SettlementItem("DFHESRTER654760",null,0,null,"",null));



        return settlementList;
    }
}
