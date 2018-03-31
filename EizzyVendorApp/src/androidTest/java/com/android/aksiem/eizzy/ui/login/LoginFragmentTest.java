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

package com.android.aksiem.eizzy.ui.login;

import android.arch.lifecycle.MutableLiveData;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.binding.FragmentBindingAdapters;
import com.android.aksiem.eizzy.testing.SingleFragmentActivity;
import com.android.aksiem.eizzy.ui.common.NavigationController;
import com.android.aksiem.eizzy.util.EspressoTestUtil;
import com.android.aksiem.eizzy.util.ViewModelUtil;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class LoginFragmentTest {
    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityRule =
            new ActivityTestRule<>(SingleFragmentActivity.class, true, true);

    private LoginViewModel viewModel;
    private NavigationController navigationController;
    private FragmentBindingAdapters fragmentBindingAdapters;
    private MutableLiveData<Resource<User>> userData = new MutableLiveData<>();

    @Before
    public void init() throws Throwable {
        EspressoTestUtil.disableProgressBarAnimations(activityRule);
        LoginFragment fragment = new LoginFragment();
        viewModel = mock(LoginViewModel.class);
        navigationController = mock(NavigationController.class);
        fragmentBindingAdapters = mock(FragmentBindingAdapters.class);

        fragment.viewModelFactory = ViewModelUtil.createFor(viewModel);
        fragment.navigationController = navigationController;
        fragment.dataBindingComponent = () -> fragmentBindingAdapters;

        activityRule.getActivity().setFragment(fragment);
    }

    @Test
    public void loading() {
        userData.postValue(Resource.loading(null));
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.retry)).check(matches(not(isDisplayed())));
    }

    @Test
    public void error() {
        userData.postValue(Resource.error("failed to login", null));
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.error_msg)).check(matches(isDisplayed()));
        onView(withId(R.id.retry)).check(matches(isDisplayed()));
        onView(withId(R.id.retry)).perform(click());
    }

}