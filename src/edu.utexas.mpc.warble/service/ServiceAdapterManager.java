/*
 * MIT License
 *
 * Copyright (c) 2018 Yosef Saputra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package edu.utexas.mpc.warble.service;

import java.util.HashMap;
import java.util.logging.Logger;

public class ServiceAdapterManager {
    private static final String TAG = ServiceAdapterManager.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    private static ServiceAdapterManager instance = new ServiceAdapterManager();

    private HashMap<SERVICE_ADAPTER_TYPE, ServiceAdapter> serviceAdapterHashMap;

    private ServiceAdapterManager() {
        serviceAdapterHashMap = new HashMap<>();

        for (SERVICE_ADAPTER_TYPE serviceAdapterType : SERVICE_ADAPTER_TYPE.values()) {
            serviceAdapterHashMap.put(serviceAdapterType, null);
        }
    }

    public static ServiceAdapterManager getInstance() {
        if (instance == null) {                         // Singleton Design Pattern
            instance = new ServiceAdapterManager();
        }
        return instance;
    }

    public ServiceAdapter getServiceAdapter(SERVICE_ADAPTER_TYPE serviceAdapterType) {
        return serviceAdapterHashMap.get(serviceAdapterType);
    }

    public void setServiceAdapter(SERVICE_ADAPTER_TYPE serviceAdapterType, ServiceAdapter serviceAdapter) {
        serviceAdapterHashMap.put(serviceAdapterType, serviceAdapter);
    }
}
