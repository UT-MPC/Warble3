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

package edu.utexas.mpc.warble3.frontend.async_tasks;

import android.os.AsyncTask;

import edu.utexas.mpc.warble3.warble.thing.component.Thing;
import edu.utexas.mpc.warble3.warble.thing.credential.ThingAccessCredential;

public class AuthenticateAsyncTask extends AsyncTask<AuthenticateAsyncTask.AuthenticateAsyncTaskParam, Void, Boolean> {
    public static class AuthenticateAsyncTaskParam {
        private Thing thing;
        private ThingAccessCredential thingAccessCredential;

        public AuthenticateAsyncTaskParam(Thing thing, ThingAccessCredential thingAccessCredential) {
            this.thing = thing;
            this.thingAccessCredential = thingAccessCredential;
        }

        public Thing getThing() {
            return thing;
        }

        public void setThing(Thing thing) {
            this.thing = thing;
        }

        public ThingAccessCredential getThingAccessCredential() {
            return thingAccessCredential;
        }

        public void setThingAccessCredential(ThingAccessCredential thingAccessCredential) {
            this.thingAccessCredential = thingAccessCredential;
        }
    }

    private AuthenticateAsyncTaskInterface mCallback;
    private AuthenticateAsyncTaskParam param;

    public AuthenticateAsyncTask(AuthenticateAsyncTaskInterface context) {
        mCallback = context;
    }

    @Override
    protected void onPreExecute() {
        mCallback.onAuthenticateTaskStart();
    }

    @Override
    protected Boolean doInBackground(AuthenticateAsyncTaskParam... params) {
        param = params[0];
        return param.thing.authenticate(param.thingAccessCredential);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        mCallback.onAuthenticateTaskComplete(result, param);
    }

    public interface AuthenticateAsyncTaskInterface {
        void onAuthenticateTaskStart();
        void onAuthenticateTaskComplete(Boolean result, AuthenticateAsyncTaskParam param);
    }
}
