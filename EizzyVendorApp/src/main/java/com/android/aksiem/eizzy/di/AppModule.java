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

package com.android.aksiem.eizzy.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.android.aksiem.eizzy.db.AppDb;
import com.android.aksiem.eizzy.db.OrderItemDao;
import com.android.aksiem.eizzy.util.Logger;

import dagger.Module;
import dagger.Provides;

@Module(includes = {
        ViewModelModule.class,
        UtilsModule.class})
class AppModule {
    private static final String APP_DB_NAME = "eizzy_vendor_app.db";

    @AppScope
    @Provides
    AppDb provideDb(Application app) {
        AppDb db = Room.databaseBuilder(app, AppDb.class, APP_DB_NAME).build();
        Logger.tag("APPMODULE_APPDB::").d(app.getDatabasePath(APP_DB_NAME).getAbsolutePath());
        return db;
    }

    @ApplicationContext
    @Provides
    Context provideAppContext(Application app) {
        return app.getApplicationContext();
    }

    @AppScope
    @Provides
    OrderItemDao provideOrderItemDao(AppDb db) {
        return db.orderItemDao();
    }

}
