package com.tasktrace.util;

public class Range {

    private final long start;

    private final long end;

    private Range(RangeBuilder builder) {
        this.start = builder.start;
        this.end = builder.end;
    }

    public long getStart() {
        return start;
    }

    public long getEnd(long fileSize) {
        return Math.min(end, fileSize - 1);
    }

    public static Range parseHttpRangeString(String httpRangeString, int defaultChunkSize) {
        if (httpRangeString == null) {
            return Range.builder().withStart(0).withEnd(defaultChunkSize).build();
        }

        int dashIndex = httpRangeString.indexOf("-");
        long startRange = Long.parseLong(httpRangeString.substring(6, dashIndex));
        String endRangeString = httpRangeString.substring(dashIndex + 1);
        if (endRangeString.isEmpty()) {
            return Range.builder()
                    .withStart(startRange)
                    .withEnd(startRange + defaultChunkSize)
                    .build();
        }

        long endRange = Long.parseLong(endRangeString);
        return Range.builder()
                .withStart(startRange)
                .withEnd(endRange)
                .build();
    }

    public static RangeBuilder builder() {
        return new RangeBuilder();
    }

    public static class RangeBuilder {
        private long start;

        private long end;

        public RangeBuilder withStart(long start) {
            this.start = start;
            return this;
        }

        public RangeBuilder withEnd(long end) {
            this.end = end;
            return this;
        }

        public Range build() {
            return new Range(this);
        }
    }
}
