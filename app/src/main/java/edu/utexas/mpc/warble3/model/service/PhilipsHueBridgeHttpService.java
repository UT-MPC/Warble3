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
import java.util.Map;

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
    public String getConfig(String user) {
        Response<ConfigurationResponse> response;
        try {
            response = api.getConfig(user).execute();
        }
        catch (IOException e) {
            if (Logging.WARN) Log.w(TAG, e.toString());
            return null;
        }

        ConfigurationResponse responseBody = response.body();

        if (responseBody != null) {
            Map<String, ConfigurationResponse.User> whitelist = responseBody.getWhitelist();

            String name = whitelist.get(user).getName();

            if (name != null){
                return name;
            }
            else {
                return null;
            }
        }
        return null;
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

    protected interface PhilipsHueBridgeRestApi {
        @POST("/api")
        @Headers({
                "Content-Type: application/json"
        })
        Call<List<CreateUserResponse>> createUser(@Body CreateUserRequest createUserRequest);

        @GET("/api/{user}/config")
        Call<ConfigurationResponse> getConfig(@Path("user") String userId);

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

    private class ConfigurationResponse {
        @SerializedName("name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        @SerializedName("zigbeechannel")
        @Expose
        private int zigbeechannel;

        public int getZigbeechannel() {
            return zigbeechannel;
        }

        @SerializedName("bridgeid")
        @Expose
        private String bridgeid;

        public String getBridgeid() {
            return bridgeid;
        }

        @SerializedName("mac")
        @Expose
        private String mac;

        public String getMac() {
            return mac;
        }

        @SerializedName("dhcp")
        @Expose
        private boolean dhcp;

        public boolean isDhcp() {
            return dhcp;
        }

        @SerializedName("ipaddress")
        @Expose
        private String ipaddress;

        public String getIpaddress() {
            return ipaddress;
        }

        @SerializedName("netmask")
        @Expose
        private String netmask;

        @SerializedName("gateway")
        @Expose
        private String gateway;

        @SerializedName("proxyaddress")
        @Expose
        private String proxyaddress;

        @SerializedName("proxyport")
        @Expose
        private int proxyport;

        @SerializedName("UTC")
        @Expose
        private String UTC;

        @SerializedName("localtime")
        @Expose
        private String localtime;

        @SerializedName("timezone")
        @Expose
        private String timezone;

        @SerializedName("modelid")
        @Expose
        private String modelid;

        @SerializedName("datastoreversion")
        @Expose
        private String datastoreversion;

        @SerializedName("swversion")
        @Expose
        private String swversion;

        @SerializedName("apiversion")
        @Expose
        private String apiversion;

        @SerializedName("swupdate")
        @Expose
        private Swupdate swupdate;

        private class Swupdate {
            @SerializedName("updatestate")
            @Expose
            private int updatestate;

            @SerializedName("checkforupdate")
            @Expose
            private boolean checkforupdate;

            @SerializedName("devicetypes")
            @Expose
            private Object devicetypes;

            @SerializedName("url")
            @Expose
            private String url;

            @SerializedName("text")
            @Expose
            private String text;

            @SerializedName("notify")
            @Expose
            private boolean notify;
        }

        @SerializedName("swupdate2")
        @Expose
        private Swupdate2 swupdate2;

        private class Swupdate2 {
            @SerializedName("checkforupdate")
            @Expose
            private boolean checkforupdate;

            @SerializedName("lastchange")
            @Expose
            private String lastchange;

            @SerializedName("bridge")
            @Expose
            private Bridge bridge;

            private class Bridge {
                @SerializedName("updatetime")
                @Expose
                private String updatetime;

                @SerializedName("on")
                @Expose
                private boolean on;
            }

            @SerializedName("state")
            @Expose
            private String state;

            @SerializedName("autoinstall")
            @Expose
            private Autoinstall autoinstall;

            private class Autoinstall {
                @SerializedName("updatetime")
                @Expose
                private String updatetime;

                @SerializedName("on")
                @Expose
                private boolean on;
            }
        }

        @SerializedName("linkbutton")
        @Expose
        private boolean linkbutton;

        @SerializedName("portalservices")
        @Expose
        private boolean portalservices;

        @SerializedName("portalconnection")
        @Expose
        private String portalconnection;

        @SerializedName("portalstate")
        @Expose
        private PortalState portalstate;

        private class PortalState {
            @SerializedName("signedon")
            @Expose
            private boolean signedon;

            @SerializedName("incoming")
            @Expose
            private boolean incoming;

            @SerializedName("outgoing")
            @Expose
            private boolean outgoing;

            @SerializedName("communication")
            @Expose
            private String communication;
        }

        @SerializedName("internetservices")
        @Expose
        private InternetServices internetservices;

        private class InternetServices {
            @SerializedName("internet")
            @Expose
            private String internet;

            @SerializedName("remoteaccess")
            @Expose
            private String remoteaccess;

            @SerializedName("time")
            @Expose
            private String time;

            @SerializedName("swupdate")
            @Expose
            private String swupdate;
        }

        @SerializedName("factorynew")
        @Expose
        private boolean factorynew;

        @SerializedName("replacesbridgeid")
        @Expose
        private String replacesbridgeid;

        @SerializedName("backup")
        @Expose
        private Backup backup;

        private class Backup {
            @SerializedName("status")
            @Expose
            private String status;

            @SerializedName("errorcode")
            @Expose
            private int errorcode;
        }

        @SerializedName("starterkitid")
        @Expose
        private String starterkitid;

        @SerializedName("whitelist")
        @Expose
        private Map<String, User> whitelist;

        public Map<String, User> getWhitelist() {
            return whitelist;
        }

        private class User {
            @SerializedName("last use date")
            @Expose
            private String lastUseDate;

            public String getLastUseDate() {
                return lastUseDate;
            }

            @SerializedName("create date")
            @Expose
            private String createDate;

            public String getCreateDate() {
                return createDate;
            }

            public String getName() {
                return name;
            }

            @SerializedName("name")
            @Expose
            private String name;
        }
    }
}
