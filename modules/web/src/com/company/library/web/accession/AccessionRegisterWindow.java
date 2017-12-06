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

package com.company.library.web.accession;

import com.company.library.entity.Book;
import com.company.library.entity.BookInstance;
import com.company.library.entity.BookPublication;
import com.company.library.service.BookInstanceService;
import com.company.library.web.components.AssignLibraryDepartmentAction;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class AccessionRegisterWindow extends AbstractWindow {

    @Inject
    private BookInstanceService bookInstanceService;

    @Inject
    private CollectionDatasource<Book, UUID> booksDs;

    @Inject
    private CollectionDatasource<BookPublication, UUID> bookPublicationsDs;

    @Inject
    private CollectionDatasource<BookInstance, UUID> bookInstancesDs;

    @Inject
    private LookupPickerField bookField;

    @Inject
    private Table<BookPublication> bookPublicationsTable;

    @Inject
    private TextField bookInstancesAmountField;

    @Inject
    private Table<BookInstance> bookInstancesTable;

    @Inject
    private Metadata metadata;

    @Override
    public void init(Map<String, Object> params) {
        bookField.addValueChangeListener(e -> bookPublicationsDs.refresh());

        addAction(new AssignLibraryDepartmentAction(bookInstancesTable));
    }

    public void createBook() {
        final Editor bookEditor = openEditor(
                "library$Book.edit", metadata.create(Book.class), WindowManager.OpenType.THIS_TAB
        );
        bookEditor.addCloseListener(actionId -> {
            booksDs.refresh();
            bookField.setValue(bookEditor.getItem());
        });
    }

    public void createBookPublication() {
        Book book = bookField.getValue();
        if (book == null) {
            showNotification(getMessage("selectBookMessage.text"), NotificationType.HUMANIZED);
            return;
        }

        BookPublication bookPublication = metadata.create(BookPublication.class);
        bookPublication.setBook(book);
        Editor bookPublicationEditor = openEditor(
                "library$BookPublication.edit", bookPublication, WindowManager.OpenType.THIS_TAB
        );
        bookPublicationEditor.addCloseListener(actionId -> bookPublicationsDs.refresh());
    }

    public void createBookInstances() {
        BookPublication bookPublication = bookPublicationsTable.getSingleSelected();
        if (bookPublication == null) {
            showNotification(getMessage("selectBookPublicationMessage.text"), NotificationType.HUMANIZED);
            return;
        }

        Integer bookInstancesAmount = bookInstancesAmountField.getValue();

        if (bookInstancesAmount == null || bookInstancesAmount == 0) {
            showNotification(getMessage("setBookInstancesAmountMessage.text"), NotificationType.HUMANIZED);
            return;
        }

        if (bookInstancesAmount > 100) {
            showNotification(getMessage("bigBookInstancesAmountMessage.text"), NotificationType.HUMANIZED);
            return;
        }

        // Create book instances in the middleware service
        Collection<BookInstance> bookInstances = bookInstanceService.createBookInstances(
                bookPublication, bookInstancesAmount);

        // Add created book instances to the datasource
        for (BookInstance bookInstance : bookInstances) {
            bookInstancesDs.includeItem(bookInstance);
        }
    }
}