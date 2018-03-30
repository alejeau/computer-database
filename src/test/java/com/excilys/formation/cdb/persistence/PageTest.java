package com.excilys.formation.cdb.persistence;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.model.Model;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PageTest {

    private static final long LAST_PAGE_NUMBER = 42L;

    private class TestObject implements Model {
        @Override
        public String shortToString() {
            return "TestObject";
        }
    }

    private class TestPage extends Page<TestObject> {
        TestPage() {
            this.list = new ArrayList<>();
            this.list.add(new TestObject());
        }

        @Override
        protected Long currentLastPageNumber() {
            return LAST_PAGE_NUMBER;
        }

        @Override
        protected void refresh(long offset) {
        }
    }

    @Test
    public void nextFromStart() throws ServiceException {
        TestPage testPage = new TestPage();
        final long EXPECTED = testPage.getPageNumber() + 1;
        testPage.next();
        assertEquals(EXPECTED, testPage.getPageNumber().longValue());
    }

    @Test
    public void nextFromEnd() throws ServiceException {
        TestPage testPage = new TestPage();
        testPage.goToPage(LAST_PAGE_NUMBER);
        testPage.next();
        assertEquals(LAST_PAGE_NUMBER, testPage.getPageNumber().longValue());
    }

    @Test
    public void previousFromFirst() throws ServiceException {
        TestPage testPage = new TestPage();
        testPage.goToPage(Page.FIRST_PAGE);
        testPage.previous();
        assertEquals(Page.FIRST_PAGE, testPage.getPageNumber());

    }

    @Test
    public void previousFromLast() throws ServiceException {
        TestPage testPage = new TestPage();
        final Long EXPECTED = LAST_PAGE_NUMBER - 1;
        testPage.goToPage(LAST_PAGE_NUMBER);
        testPage.previous();
        assertEquals(EXPECTED, testPage.getPageNumber());
    }

    @Test
    public void goToLastPage() throws ServiceException {
        TestPage testPage = new TestPage();
        testPage.goToPage(LAST_PAGE_NUMBER);
        assertEquals(LAST_PAGE_NUMBER, testPage.getPageNumber().longValue());
    }

    @Test
    public void goToBeyondLastPage() throws ServiceException {
        TestPage testPage = new TestPage();
        testPage.goToPage(LAST_PAGE_NUMBER + 1);
        assertEquals(LAST_PAGE_NUMBER, testPage.getPageNumber().longValue());
    }

    @Test
    public void goToBeyondFirstPage() throws ServiceException {
        TestPage testPage = new TestPage();
        testPage.goToPage(Page.FIRST_PAGE - 1);
        assertEquals(Page.FIRST_PAGE, testPage.getPageNumber());
    }
}
