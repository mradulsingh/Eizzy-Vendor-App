/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.aksiem.eizzy.ui.order;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.NavigationFragment;
import com.android.aksiem.eizzy.binding.FragmentDataBindingComponent;
import com.android.aksiem.eizzy.databinding.CreateOrderFragmentBinding;
import com.android.aksiem.eizzy.databinding.EizzyZoneListBinding;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.ui.common.ToastController;
import com.android.aksiem.eizzy.ui.toolbar.AppToolbarBuilder;
import com.android.aksiem.eizzy.ui.toolbar.NavigationBuilder;
import com.android.aksiem.eizzy.util.AutoClearedValue;
import com.android.aksiem.eizzy.vo.EizzyZone;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by napendersingh on 31/03/18.
 */

public class CreateOrderFragment extends NavigationFragment {

    private static final String BUNDLE_EIZZY_ZONES_KEY = "bundleEizzyZonesKey";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    AutoClearedValue<CreateOrderFragmentBinding> binding;

    private CreateOrderViewModel createOrderViewModel;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private AlertDialog eizzyZoneDialog;

    @Inject
    ToastController toastController;

    protected DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    private ArrayList<EizzyZone> eizzyZones;

    public static CreateOrderFragment newInstance(ArrayList<EizzyZone> eizzyZones) {

        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_EIZZY_ZONES_KEY, eizzyZones);
        CreateOrderFragment fragment = new CreateOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public NavigationBuilder buildNavigation() {
        return AppToolbarBuilder.mainToolbarWithBottomAction()
                .toolbarTitleRes(R.string.screen_title_create_order)
                .toolbarNavIconRes(R.drawable.ic_back)
                .setToolbarNavClickListener(v -> onBackPressed())
                .setBottomActionTitleRes(R.string.button_action_create_order)
                .setBottomActionClickHandler((v, args) -> onBottomActionClicked(v));
    }

    private void onBottomActionClicked(View view) {
        toastController.showErrorToast("Order Creation Failed");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CreateOrderFragmentBinding dataBinding = DataBindingUtil
                .inflate(inflater, R.layout.create_order_fragment, container, false,
                        dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return wrapNavigationLayout(inflater, container, dataBinding.getRoot());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createOrderViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CreateOrderViewModel.class);
        init(savedInstanceState);
    }

    private void init(@Nullable Bundle savedInstanceState) {
        initData(savedInstanceState);
        initListeners();
    }

    private void initData(@Nullable Bundle savedInstanceState) {
        Bundle args = savedInstanceState == null ? getArguments() : savedInstanceState;
        eizzyZones = (ArrayList<EizzyZone>) args.getSerializable(BUNDLE_EIZZY_ZONES_KEY);
    }

    private void initListeners() {
        initEizzyZoneListener();
        initScheduleDetailListener();
        initDateInputListener();
        initTimeInputListener();
    }

    private void initEizzyZoneListener() {
        binding.get().eizzyZone.setOnFocusChangeListener((v, hasFocus) -> {

            dismissKeyboard(v.getWindowToken());

            if (hasFocus) {
                if (eizzyZoneDialog == null) {
                    EizzyZoneListBinding eizzyZoneListBinding = DataBindingUtil.inflate(
                            LayoutInflater.from(getContext()), R.layout.eizzy_zone_list,
                            null, false);
                    EizzyZoneAdapter adapter = new EizzyZoneAdapter(getContext(),
                            R.layout.eizzy_zone_item, eizzyZones);
                    eizzyZoneListBinding.eizzyZoneList.setAdapter(adapter);
                    eizzyZoneListBinding.eizzyZoneList.setOnItemClickListener((parent, view,
                                                                               position, id) -> {
                        binding.get().eizzyZone.setText(adapter.getItem(position).name);
                        if (eizzyZoneDialog != null && eizzyZoneDialog.isShowing()) {
                            eizzyZoneDialog.dismiss();
                            v.clearFocus();
                        }
                    });
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setView(eizzyZoneListBinding.getRoot());
                    builder.setTitle(getString(R.string.edittext_hint_eizzy_zone));
                    eizzyZoneDialog = builder.create();
                }
                eizzyZoneDialog.show();
            }

        });
    }

    private void initScheduleDetailListener() {
        binding.get().switchScheduleDetails.setOnCheckedChangeListener((buttonView, isChecked) -> {
            toggleSchedulingVisibility(isChecked);
        });
    }

    private void toggleSchedulingVisibility(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        binding.get().orderDate.setVisibility(visibility);
        binding.get().orderTime.setVisibility(visibility);
    }

    private void initDateInputListener() {
        binding.get().orderDate.setOnFocusChangeListener((v, hasFocus) -> {

            dismissKeyboard(v.getWindowToken());

            if (hasFocus) {
                if (datePickerDialog == null) {
                    Calendar c = Calendar.getInstance();
                    datePickerDialog = new DatePickerDialog(getContext(), (view, year, month,
                                                                           dayOfMonth) -> {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        binding.get().orderTime.setText(formatter.format(newDate.getTime()));
                        v.clearFocus();

                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMinDate(new Date().getTime() - 1000);
                }
                datePickerDialog.show();
            }
        });
    }

    private void initTimeInputListener() {
        binding.get().orderTime.setOnFocusChangeListener((v, hasFocus) -> {

            dismissKeyboard(v.getWindowToken());

            if (hasFocus) {
                if (timePickerDialog == null) {
                    Calendar c = Calendar.getInstance();
                    timePickerDialog = new TimePickerDialog(getContext(), (view, hourOfDay,
                                                                           minute) -> {
                        binding.get().orderTime.setText(hourOfDay + ":" + minute);
                        v.clearFocus();
                    }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
                }
                timePickerDialog.show();
            }
        });
    }

    private void createOrderListing(View v) {
        // Dismiss keyboard
        dismissKeyboard(v.getWindowToken());
    }
}
