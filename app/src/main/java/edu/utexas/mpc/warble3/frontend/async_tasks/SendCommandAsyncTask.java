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

import edu.utexas.mpc.warble3.util.WarbleHandler;
import edu.utexas.mpc.warble3.warble.thing.command.Command;
import edu.utexas.mpc.warble3.warble.thing.command.Response;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;

public class SendCommandAsyncTask extends AsyncTask<Void, Void, Response> {
    private SendCommandAsyncTaskInterface callback;
    private Command command;
    private Thing thing;

    public SendCommandAsyncTask(Command command, Thing thing) {
        this.command = command;
        this.thing = thing;
    }

    @Override
    protected Response doInBackground(Void... voids) {
        return WarbleHandler.getInstance().sendCommand(command, thing);
    }

    @Override
    protected void onPostExecute(Response response) {
        if (callback != null) {
            callback.onComplete(response);
        }
    }

    public void setCallback(SendCommandAsyncTaskInterface callback) {
        this.callback = callback;
    }

    public interface SendCommandAsyncTaskInterface {
        void onComplete(Response response);
    }
}
