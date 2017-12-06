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

package com.company.library.service;

import com.company.library.entity.BookInstance;
import com.company.library.entity.BookPublication;
import com.company.library.entity.LibraryDepartment;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.app.UniqueNumbersAPI;
import com.haulmont.cuba.core.global.AccessDeniedException;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.Security;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.security.entity.EntityOp;
import com.haulmont.cuba.security.entity.PermissionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

@Service(BookInstanceService.NAME)
public class BookInstanceServiceBean implements BookInstanceService {

    @Inject
    private Persistence persistence;

    @Inject
    private UniqueNumbersAPI uniqueNumbers;

    @Inject
    private Security security;

    @Inject
    private Metadata metadata;

    @Override
    @Transactional
    public Collection<BookInstance> createBookInstances(BookPublication bookPublication, Integer amount) {
        checkPermission(EntityOp.CREATE);
        // Due to the @Transactional annotation a new transaction is started right before the method is called and
        // committed after leaving the method
        Collection<BookInstance> bookInstances = new ArrayList<>();
        for (int i = 0; i < amount; ++i) {
            BookInstance bookInstance = metadata.create(BookInstance.class);
            bookInstance.setBookPublication(bookPublication);
            bookInstance.setInventoryNumber(uniqueNumbers.getNextNumber("inventory_number"));

            persistence.getEntityManager().persist(bookInstance);

            bookInstances.add(bookInstance);
        }
        return bookInstances;
    }

    @Override
    public Collection<BookInstance> assignLibraryDepartment(Collection<BookInstance> bookInstances, LibraryDepartment libraryDepartment, View bookInstanceView) {
        checkPermission(EntityOp.UPDATE);
        Collection<BookInstance> mergedInstances = new ArrayList<>();
        // Explicit transaction control
        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();
            for (BookInstance booksInstance : bookInstances) {
                BookInstance instance = em.merge(booksInstance, bookInstanceView);
                instance.setLibraryDepartment(libraryDepartment);
                // Return the updated instance
                mergedInstances.add(instance);
            }
            tx.commit();
        }
        return mergedInstances;
    }

    private void checkPermission(EntityOp op) {
        if (!security.isEntityOpPermitted(BookInstance.class, op)) {
            throw new AccessDeniedException(PermissionType.ENTITY_OP, BookInstance.class.getSimpleName());
        }
    }
}
