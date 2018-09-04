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

package edu.utexas.mpc.warble3.warble.thing.command;

import edu.utexas.mpc.warble3.database.AppDatabase;
import edu.utexas.mpc.warble3.database.InteractionHistoryDb;
import edu.utexas.mpc.warble3.warble.thing.component.Thing;

public class GenericCommandCaller extends CommandCaller {
    public GenericCommandCaller(Command command, Thing thing) {
        super(command, thing);
    }

    @Override
    public Response call() {
        InteractionHistoryDb interactionHistoryDb = new InteractionHistoryDb();
        interactionHistoryDb.setCommand(getCommand().toString());
        interactionHistoryDb.setThingUuid(getThing().getUuid());
        if (getThing().getLocation() != null)
            interactionHistoryDb.setThingLocation(getThing().getLocation().toString());
        AppDatabase.getDatabase().saveInteractionHistoryDb(interactionHistoryDb);

        return getThing().callCommand(getCommand());
    }
}
