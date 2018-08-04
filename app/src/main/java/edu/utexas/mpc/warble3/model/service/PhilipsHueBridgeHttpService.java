package edu.utexas.mpc.warble3.model.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

import edu.utexas.mpc.warble3.model.thing.component.manufacturer.PhilipsHue.PhilipsHueBridgeHttpInterface;
import edu.utexas.mpc.warble3.model.thing.component.manufacturer.PhilipsHue.PhilipsHueLight;
import edu.utexas.mpc.warble3.model.thing.component.manufacturer.PhilipsHue.PhilipsHueLightState;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public final class PhilipsHueBridgeHttpService extends HttpService implements PhilipsHueBridgeHttpInterface {
    private PhilipsHueBridgeRestApi api;

    public PhilipsHueBridgeHttpService(String baseUrl) {
        api = getRetrofitInstance(baseUrl).create(PhilipsHueBridgeRestApi.class);
    }

    @Override
    public String createUser(String username) {
        return null;
    }

    @Override
    public String getUserInfo(String user) {
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
