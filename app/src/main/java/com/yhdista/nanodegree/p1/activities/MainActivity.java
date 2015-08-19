/*
 * Copyright (C) 2013 The Android Open Source Project
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

package com.yhdista.nanodegree.p1.activities;

import android.os.Bundle;

import com.yhdista.nanodegree.p1.R;
import com.yhdista.nanodegree.p1.abstracts.MyAppCompatActivity;
import com.yhdista.nanodegree.p1.utils.UtilsActivity;

/**
 *  Main Activity as the list (grid) of Movies
 */
public class MainActivity extends MyAppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UtilsActivity.setToolbarAsSupportActionBar(this, mToolbar, R.id.my_toolbar);


    }


}
