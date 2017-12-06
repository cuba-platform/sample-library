/*
 * Copyright (c) 2017 Haulmont
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.company.library.web.components;

import com.company.library.entity.BookInstance;
import com.company.library.web.department_assigning.DepartmentAssigning;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Action that allows to assign library department to book instances, selected in a table.
 * <p>
 * This action is used by BookInstanceBrowse and AccessionRegisterWindow.
 */
public class AssignLibraryDepartmentAction extends AbstractAction {

    private Table<BookInstance> booksInstancesTable;

    public AssignLibraryDepartmentAction(Table<BookInstance> booksInstancesTable) {
        super("assignLibraryDepartment");
        this.booksInstancesTable = booksInstancesTable;
    }

    @Override
    public void actionPerform(Component component) {
        Frame frame = booksInstancesTable.getFrame();

        Set<BookInstance> bookInstances = booksInstancesTable.getSelected();

        if (!bookInstances.isEmpty()) {
            // Parameters passed to DepartmentAssigning window
            Map<String, Object> params = new HashMap<>();
            params.put(DepartmentAssigning.INSTANCES_PARAM, bookInstances);
            params.put(DepartmentAssigning.VIEW_PARAM, booksInstancesTable.getDatasource().getView());

            final DepartmentAssigning departmentAssigningWindow = (DepartmentAssigning) frame.openWindow("department-assigning",
                    WindowManager.OpenType.DIALOG.width((float)400), params);
            departmentAssigningWindow.addListener(actionId -> {
                if (DepartmentAssigning.SUCCESS_ACTION.equals(actionId)) {
                    for (BookInstance assignedInstance : departmentAssigningWindow.getAssignedInstances()) {
                        // Put returned instances back into datasource
                        booksInstancesTable.getDatasource().updateItem(assignedInstance);
                    }
                }
            });
        } else {
            frame.showNotification(messages.getMainMessage("selectBookInstancesMessage.text"),
                    Frame.NotificationType.HUMANIZED);
        }
    }
}
