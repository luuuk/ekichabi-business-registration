package com.ekichabi_business_registration.screens.stereotype;

import java.util.List;

public abstract class PaginationScreen extends SimpleScreen {

    private static final int NUM_ITEMS = 5;
    private static final int SELECT_NEXT_PAGE = 99;
    private static final int SELECT_PREV_PAGE = 0;

    private final List<String> options;
    private final int numPages;
    private final String title;
    private int currentPage;

    PaginationScreen(List<String> options, String title, int currentPage) {
        super(true);
        this.options = options;
        this.currentPage = currentPage;
        this.numPages = (options.size() - 1) / NUM_ITEMS + 1;
        this.title = title;

        addAction(s -> {
            int i;
            try {
                i = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return null;
            }
            if (1 <= i && i <= NUM_ITEMS) {
                return selected(currentPage * NUM_ITEMS + i - 1);
            } else if (i == SELECT_NEXT_PAGE && currentPage != numPages - 1) {
                this.currentPage++;
                return this;
            } else if (i == SELECT_PREV_PAGE && currentPage != 0) {
                this.currentPage--;
                return this;
            } else {
                return null;
            }
        });
    }

    PaginationScreen(List<String> options, String title) {
        this(options, title, 0);
    }

    protected abstract Screen selected(int i);

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title)
                .append(" (").append(currentPage)
                .append("/").append(numPages).append(")")
                .append("\n");

        for (int i = currentPage * NUM_ITEMS; i < (currentPage + 1) * NUM_ITEMS; i++) {
            // fill additional lines with "" to make sure the pagination option
            // is at the bottom
            sb.append(i < options.size() ? options.get(i) : "").append("\n");
        }

        if (currentPage != 0 && currentPage != numPages - 1) {
            sb.append("0. Back\n");
            sb.append("99. Next\n");
        } else if (currentPage == 0 && currentPage == numPages - 1) {
            sb.append("\n");
            sb.append("\n");
        } else if (currentPage == 0) {
            sb.append("\n");
            sb.append("99. Next\n");
        } else if (currentPage == numPages - 1) {
            sb.append("\n");
            sb.append("0. Back\n");
        }
        return sb.toString();
    }
}
