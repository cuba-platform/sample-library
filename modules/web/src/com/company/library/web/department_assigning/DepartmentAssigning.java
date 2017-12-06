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

package com.company.library.web.department_assigning;

import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.WindowParam;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.LookupField;
import com.company.library.entity.BookInstance;
import com.company.library.entity.LibraryDepartment;
import com.company.library.service.BookInstanceService;

import javax.inject.Inject;
import java.util.Collection;

public class DepartmentAssigning extends AbstractWindow {

    public static final String INSTANCES_PARAM = "bookInstances";
    public static final String VIEW_PARAM = "bookInstanceView";

    public static final String SUCCESS_ACTION = "success";

    @Inject
    private BookInstanceService bookInstanceService;

    @Inject
    private LookupField libraryDepartmentField;

    @WindowParam(name = INSTANCES_PARAM, required = true)
    private Collection<BookInstance> bookInstances;

    @WindowParam(name = VIEW_PARAM, required = true)
    private View bookInstanceView;

    private Collection<BookInstance> assignedInstances;

    public void assign() {
        LibraryDepartment libraryDepartment = libraryDepartmentField.getValue();
        if (libraryDepartment != null) {
            assignedInstances = bookInstanceService.assignLibraryDepartment(
                    bookInstances, libraryDepartment, bookInstanceView);
            close(SUCCESS_ACTION);
        } else {
            showNotification(getMessage("selectLibraryDepartmentMessage.text"), NotificationType.HUMANIZED);
        }
    }

    public void close() {
        close("");
    }

    public Collection<BookInstance> getAssignedInstances() {
        return assignedInstances;
    }
}