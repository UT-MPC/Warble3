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
 *
 */

package edu.utexas.mpc.warble3.model.service;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import edu.utexas.mpc.warble3.model.thing.component.manufacturer.PhilipsHue.PhilipsHueBridgeHttpInterface;
import edu.utexas.mpc.warble3.model.thing.component.manufacturer.PhilipsHue.PhilipsHueLight;
import edu.utexas.mpc.warble3.model.thing.component.manufacturer.PhilipsHue.PhilipsHueLightState;
import edu.utexas.mpc.warble3.util.Logging;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public final class PhilipsHueBridgeHttpService extends HttpService implements PhilipsHueBridgeHttpInterface {
    private static String TAG = "PhilipsHueBridgeHttpService";

    private PhilipsHueBridgeRestApi api;

    public PhilipsHueBridgeHttpService(String baseUrl) {
        super();
        api = getRetrofitInstance(baseUrl).create(PhilipsHueBridgeRestApi.class);
    }

    @Override
    public String createUser(String username) {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setDevicetype(String.format("%s", username));

        String returnVal = null;
        Response<List<CreateUserResponse>> response;
        try {
            response = api.createUser(createUserRequest).execute();
        }
        catch (IOException e) {
            if (Logging.WARN) Log.w(TAG, e.toString());
            return null;
        }

        List<CreateUserResponse> responseBody = response.body();

        if (responseBody != null) {
            for (CreateUserResponse createUserResponse : responseBody) {
                try {
                    CreateUserResponse.Success success = createUserResponse.getSuccess();
                    returnVal = success.getUsername();
                    if (Logging.INFO) Log.i(TAG, String.format("Create User succeed. Token: %s", success.getUsername()));
                } catch (NullPointerException e) {
                    CreateUserResponse.Error error = createUserResponse.getError();
                    if (Logging.WARN) Log.w(TAG, String.format("Create User failed. Reason: %s", error.getDescription()));
                }
            }
            return returnVal;
        }
        else {
            return null;
        }
    }

    @Override
    public String getUserInfo(String user) {
        Response response;
        try {
            response = api.getInfo(user).execute();
        }
        catch (IOException e) {
            if (Logging.WARN) Log.w(TAG, e.toString());
            return null;
        }

        return (String) response.body();
    }

    @Override
    public List<PhilipsHueLight> getLights(String user) {
        return null;
    }

    @Override
    public PhilipsHueLightState getLightState(String user, PhilipsHueLight philipsHueLight) {
        return null;
    }

    @Override
    public void putLight(String user, PhilipsHueLight philipsHueLight, PhilipsHueLightState philipsHueLightState) {

    }

    protected interface PhilipsHueBridgeRestApi extends HttpService.RestApi {
        @POST("/api")
        @Headers({
                "Content-Type: application/json"
        })
        Call<List<CreateUserResponse>> createUser(@Body CreateUserRequest createUserRequest);

        @GET("/api/{user}")
        Call<ResponseBody> getInfo(@Path("user") String userId);

        @GET("/api/{user}/lights")
        Call<ResponseBody> getLights(@Path("user") String userId);

        // Lights Capabilities
        @GET("/api/{user}/lights/{lightId}/state")
        Call<List<Object>> getLightState(@Path("user") String userId, @Path("lightId") int lightId);

        @PUT("/api/{user}/lights/{lightId}")
        @Headers({
                "Content-Type: application/json"
        })
        Call<List<Object>> putLight(@Path("user") String userId, @Path("lightId") String lightId, @Body HashMap<String, Object> light);

        @PUT("/api/{user}/lights/{lightId}/state")
        @Headers({
                "Content-Type: application/json"
        })
        Call<List<Object>> putLightState(@Path("user") String userId, @Path("lightId") String lightId, @Body HashMap<String, Object> lightState);
    }

    private class CreateUserRequest {
        @SerializedName("devicetype")
        @Expose
        private String devicetype;

        public String getDevicetype(){
            return devicetype;
        }

        public void setDevicetype(String devicetype){
            this.devicetype = devicetype;
        }
    }


    private class CreateUserResponse {
        @SerializedName("success")
        @Expose
        private Success success;

        public Success getSuccess() {
            return success;
        }

        public class Success {
            @SerializedName("username")
            @Expose
            private String username;

            public String getUsername() {
                return username;
            }
        }

        @SerializedName("error")
        @Expose
        private Error error;

        public Error getError() {
            return error;
        }

        public class Error {
            @SerializedName("type")
            @Expose
            private String type;

            public String getType() {
                return type;
            }

            @SerializedName("address")
            @Expose
            private String address;

            public String getAddress() {
                return address;
            }

            @SerializedName("description")
            @Expose
            private String description;

            public String getDescription() {
                return description;
            }
        }
    }
}
