package com.datamanager;

/**
 * @author  : pgajjar
 * @since   : 2/5/17
 */
public final class ComparisionResult {
    private final boolean md5Matches;
    private final boolean fileNameMatches;
    private final boolean fileTypeMatches;

    public ComparisionResult(final boolean md5Matches, final boolean fileNameMatches, final boolean fileTypeMatches) {
        this.md5Matches = md5Matches;
        this.fileNameMatches = fileNameMatches;
        this.fileTypeMatches = fileTypeMatches;
    }

    public boolean md5Matches() {
        return md5Matches;
    }

    public boolean fileNameMatches() {
        return fileNameMatches;
    }

    public boolean fileTypeMatches() {
        return fileTypeMatches;
    }
}
