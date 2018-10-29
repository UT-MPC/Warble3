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

package thing.command;

import context.Context;
import interaction.Interaction;
import service.BaseDatabaseServiceAdapter;
import service.SERVICE_ADAPTER_TYPE_OUTPUT;
import service.ServiceAdapterManager;
import service.ServiceAdapterUser;
import thing.component.Thing;
import user.User;

import java.util.logging.Logger;

public final class GenericCommandCaller extends CommandCaller implements ServiceAdapterUser {
    private static final String TAG = GenericCommandCaller.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    private ServiceAdapterManager serviceAdapterManager;
    private BaseDatabaseServiceAdapter databaseServiceAdapter;
    private User user;

    public GenericCommandCaller(ServiceAdapterManager serviceAdapterManager, User user) {
        this.serviceAdapterManager = serviceAdapterManager;
        this.user = user;
    }

    public void setServiceAdapterManager(ServiceAdapterManager serviceAdapterManager) {
        this.serviceAdapterManager = serviceAdapterManager;
    }

    @Override
    public Response call(Context context, Command command, Thing thing) {
        databaseServiceAdapter.saveInteraction(
                new Interaction(
                        context,
                        context.getUser().getUsername(),
                        command.toString(),
                        thing.getUuid()
                )
        );

        return thing.callCommand(serviceAdapterManager, command);
    }

    public void setDatabaseServiceAdapter(BaseDatabaseServiceAdapter databaseServiceAdapter) {
        this.databaseServiceAdapter = databaseServiceAdapter;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void setServiceAdapter(ServiceAdapterManager serviceAdapterManager) {
        this.serviceAdapterManager = serviceAdapterManager;
        this.databaseServiceAdapter = (BaseDatabaseServiceAdapter) this.serviceAdapterManager.getServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT.DATABASE);
    }
}
