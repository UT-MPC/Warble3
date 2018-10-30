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

package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.logging.Logger;

public abstract class BaseHttpServiceAdapter extends ServiceAdapter {
    private static final String TAG = BaseHttpServiceAdapter.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    public BaseHttpServiceAdapter() {
        super();
    }

//    protected Retrofit getInstance(String baseUrl) {
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//
//        return new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//    }

    public abstract <T> HttpResponse<T> sendSynchronous(String baseUrl,
                                                        String apiPath,
                                                        METHOD method,
                                                        String[] headers,
                                                        String body,
                                                        HashMap<String, Object> parts
    );

    public abstract void sendAsynchronous(Callback callback,
                                          String baseUrl,
                                          String apiPath,
                                          METHOD method,
                                          String[] headers,
                                          String body,
                                          HashMap<String, Object> parts
    );

    public interface Callback<T> {
        void onResponse(HttpResponse<T> response);
        void onFailure(Throwable t);
    }

    public enum METHOD {
        GET,
        POST,
        DELETE,
        PUT,
        HEAD,
        TRACE,
        CONNECT,
        OPTIONS
    }

    public class HttpResponse<T> {
        private int code;
        private String[] headers;
        private T body;

        public HttpResponse(int code, String[] headers, String body) {
            this.code = code;
            this.headers = headers;
            setBody(body);
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String[] getHeaders() {
            return headers;
        }

        public void setHeaders(String[] headers) {
            this.headers = headers;
        }

        public T getBody() {
            return body;
        }

        public void setBody(T body) {
            this.body = body;
        }

        public void setBody(String body) {
            this.body = new Gson().fromJson(body, new TypeToken<T>(){}.getType());
        }
    }
}
