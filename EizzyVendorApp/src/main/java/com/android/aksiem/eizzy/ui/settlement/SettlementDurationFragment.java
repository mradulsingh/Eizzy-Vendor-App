package com.android.aksiem.eizzy.ui.settlement;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.NavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.SettlementDurationFragmentBinding;
import com.android.aksiem.eizzy.di.ApplicationContext;
import com.android.aksiem.eizzy.repository.SettlementRepository;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.ui.toolbar.MenuToastAction;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.ui.toolbar.ToolbarMenuUtil;
import com.android.aksiem.eizzy.ui.toolbar.menu.MenuActions;
import com.android.aksiem.eizzy.util.AutoClearedValue;
import com.android.aksiem.eizzy.vo.settlement.SettlementItem;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.android.aksiem.eizzy.ui.toolbar.CollapsableToolbarBuilder.mainCollapsableToolbar;


public class SettlementDurationFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    static String TAG = SettlementDurationFragment.class.getSimpleName();
    private SettlementViewModel settlementViewModel;
    SettlementItemAdapter settlementItemAdapter;
    List<SettlementItem> settlementItems;
    View v;
    //@Inject
    // NavigationController navigationController;
    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    //AutoClearedValue<SettlementDurationFragmentBinding> binding;
    //AutoClearedValue<SettlementItemAdapter> adapter;

    RecyclerView rvSettlement;

    public static SettlementDurationFragment create() {
        return new SettlementDurationFragment();
    }

    @Inject
    @ApplicationContext
    Context applicationContext;

  /*  @Override
    public NavigationBuilder buildNavigation() {
        return mainCollapsableToolbar()
                .includeBottomNavBar(true)
                .includeDrawerNav(true)
                .toolbarTitleRes(R.string.screen_title_settlements)
                .toolbarNavIconRes(R.drawable.ic_back)
                .setToolbarNavClickListener(v -> onBackPressed())
                .menuRes(ToolbarMenuUtil.generateMenuFrom(R.menu.menu_settlement_fragment),
                        buildMenuActions());
    }*/

    private MenuActions buildMenuActions() {
        return new MenuActions.Builder()
                .action(R.id.nav_checkout, new MenuToastAction(applicationContext,
                        "Do Checkout"))
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

       /* SettlementDurationFragmentBinding dataBinding = DataBindingUtil.inflate(inflater,
                R.layout.settlement_duration_fragment, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        Log.e("TESTO","FragmentDuration");
        //Toast.makeText(applicationContext, getArguments().getInt("selected_duration") + "", Toast.LENGTH_SHORT).show();
*/
        Log.e(TAG, "  onCreateView");
        v = inflater.inflate(R.layout.settlement_duration_fragment, container, false);
        rvSettlement =  v.findViewById(R.id.rv_settlement);
        initSettlementItemList();
        loadListsData();
        return v;
        //return dataBinding.getRoot();//wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*settlementViewModel = ViewModelProviders.of(this, viewModelFactory).get(
                SettlementViewModel.class);*/


    }

    private void initSettlementItemList() {
        Log.e(TAG, "  initSettlementItemList");
        settlementItems = new SettlementRepository().getSettlementList();
        settlementItemAdapter = new SettlementItemAdapter(settlementItems);
    }

    private void loadListsData() {
        Log.e(TAG, "  loadListsData");
        rvSettlement.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSettlement.setAdapter(settlementItemAdapter);


    }

}
