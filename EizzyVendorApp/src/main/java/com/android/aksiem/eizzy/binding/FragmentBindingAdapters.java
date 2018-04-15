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

package com.android.aksiem.eizzy.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.aksiem.eizzy.util.AppFontManager;
import com.android.aksiem.eizzy.view.CustomHint;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

/**
 * Binding adapters that work with a fragment instance.
 */
public class FragmentBindingAdapters {
    final Fragment fragment;

    @Inject
    public FragmentBindingAdapters(Fragment fragment) {
        this.fragment = fragment;
    }

    @BindingAdapter("imageUrl")
    public void bindImage(ImageView imageView, String url) {
        Glide.with(fragment).load(url).into(imageView);
    }

    @BindingAdapter("etHintFont")
    public void bindEtHintFont(EditText editText, int fontType) {
        Context context = editText.getContext();
        Typeface hintTypeface = AppFontManager.getTypeface(context, fontType);
        CustomHint customHint = new CustomHint(hintTypeface, editText.getHint(), editText.getTextSize());
        editText.setHint(customHint);
    }
}
