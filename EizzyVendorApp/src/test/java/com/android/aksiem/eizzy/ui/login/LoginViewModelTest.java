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

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.android.aksiem.eizzy.repository.LoginRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.mockito.Mockito.mock;

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class LoginViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private LoginViewModel loginViewModel;
    private LoginRepository loginRepository;

    @Before
    public void setup() {
        loginRepository = mock(LoginRepository.class);
        loginViewModel = new LoginViewModel();
    }

    @Test
    public void testNull() {
    }

    @Test
    public void userLogin() {
    }

    @Test
    public void retry() {

    }

    @Test
    public void nullUser() {

    }

}