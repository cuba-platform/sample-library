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

package com.company.library.web.bookpublication;

import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Table;
import com.company.library.entity.BookPublication;

import javax.inject.Inject;
import java.util.Collections;

public class BookPublicationBrowse extends AbstractLookup {

    @Inject
    Table<BookPublication> bookPublicationTable;

    public void browseInstances() {
        BookPublication bookPublication = bookPublicationTable.getSingleSelected();
        if (bookPublication != null) {
            openWindow("library$BookInstance.lookup", WindowManager.OpenType.THIS_TAB,
                    Collections.singletonMap("bookPublication", bookPublication));
        } else {
            showNotification(getMessage("selectBookPublicationMessage.text"), NotificationType.HUMANIZED);
        }
    }
}