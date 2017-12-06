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

package com.company.library.entity;

import javax.persistence.*;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

@NamePattern("%s|inventoryNumber")
@Table(name = "LIBRARY_BOOK_INSTANCE")
@Entity(name = "library$BookInstance")
public class BookInstance extends StandardEntity {
    private static final long serialVersionUID = -3892225947841274259L;

    @Column(name = "IS_REFERENCE")
    private Boolean isReference;

    @Column(name = "INVENTORY_NUMBER")
    private Long inventoryNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOK_PUBLICATION_ID")
    private BookPublication bookPublication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LIBRARY_DEPARTMENT_ID")
    private LibraryDepartment libraryDepartment;

    public void setIsReference(Boolean isReference) {
        this.isReference = isReference;
    }

    public Boolean getIsReference() {
        return isReference;
    }

    public void setInventoryNumber(Long inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public Long getInventoryNumber() {
        return inventoryNumber;
    }

    public void setBookPublication(BookPublication bookPublication) {
        this.bookPublication = bookPublication;
    }

    public BookPublication getBookPublication() {
        return bookPublication;
    }

    public void setLibraryDepartment(LibraryDepartment libraryDepartment) {
        this.libraryDepartment = libraryDepartment;
    }

    public LibraryDepartment getLibraryDepartment() {
        return libraryDepartment;
    }

}