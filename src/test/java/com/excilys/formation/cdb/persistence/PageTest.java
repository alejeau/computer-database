package com.excilys.formation.cdb.persistence;

import com.excilys.formation.cdb.model.Model;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PageTest {

    private static final long LAST_PAGE_NUMBER = 42l;

    private class TestObject implements Model {
        @Override
        public String shortToString() {
            return "TestObject";
        }
    }

    private class TestPage extends Page<TestObject> {
        TestPage() {
            this.page = new ArrayList<>();
            this.page.add(new TestObject());
        }

        public TestPage(LimitValue limit) {
            super(limit);
            this.page = new ArrayList<>();
            this.page.add(new TestObject());
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
    public void nextFromStart() {
        TestPage testPage = new TestPage();
        final long EXPECTED = testPage.getPageNumber() + 1;
        testPage.next();
        assertEquals(testPage.getPageNumber().longValue(), EXPECTED);
    }

    @Test
    public void nextFromEnd() {
        TestPage testPage = new TestPage();
        testPage.goToPage(LAST_PAGE_NUMBER);
        testPage.next();
        assertEquals(testPage.getPageNumber().longValue(), LAST_PAGE_NUMBER);
    }

    @Test
    public void previousFromFirst() {
        TestPage testPage = new TestPage();
        testPage.goToPage(Page.FIRST_PAGE);
        testPage.previous();
        assertEquals(testPage.getPageNumber(), Page.FIRST_PAGE);

    }

    @Test
    public void previousFromLast() {
        TestPage testPage = new TestPage();
        final Long EXPECTED = LAST_PAGE_NUMBER - 1;
        testPage.goToPage(LAST_PAGE_NUMBER);
        testPage.previous();
        assertEquals(testPage.getPageNumber(), EXPECTED);
    }

    @Test
    public void goToLastPage() {
        TestPage testPage = new TestPage();
        testPage.goToPage(LAST_PAGE_NUMBER);
        assertEquals(testPage.getPageNumber().longValue(), LAST_PAGE_NUMBER);
    }

    @Test
    public void goToBeyondLastPage() {
        TestPage testPage = new TestPage();
        testPage.goToPage(LAST_PAGE_NUMBER + 1);
        assertEquals(testPage.getPageNumber().longValue(), LAST_PAGE_NUMBER);
    }

    @Test
    public void goToBeyondFirstPage() {
        TestPage testPage = new TestPage();
        testPage.goToPage(Page.FIRST_PAGE - 1);
        assertEquals(testPage.getPageNumber(), Page.FIRST_PAGE);
    }
}
