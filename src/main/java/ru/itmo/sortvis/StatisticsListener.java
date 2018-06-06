package ru.itmo.sortvis;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

public interface StatisticsListener {

    void statsUpdated(AllStats allStats);

    class AllStats {
        public final UpdateStatistics updateStatistics;
        public final GsGraphAdapter.Statistics statistics;

        public AllStats(UpdateStatistics updateStatistics, GsGraphAdapter.Statistics statistics) {
            this.updateStatistics = updateStatistics;
            this.statistics = statistics;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("updateStatistics", updateStatistics)
                    .append("statistics", statistics)
                    .toString();
        }

        private static class StringStyle extends ToStringStyle {
            public StringStyle() {
                super();
                this.setUseIdentityHashCode(false);
                this.setUseClassName(false);
                this.setFieldSeparator("<br>");
                this.setFieldNameValueSeparator("=");
                this.setContentStart("<html>");
                this.setContentEnd("</html>");
            }
        }

        public String asString() {
            ToStringBuilder builder = new ToStringBuilder(this, new StringStyle());
            if (updateStatistics != null && updateStatistics.getStat() != null) {
                for (Map.Entry<String, Object> entry : updateStatistics.getStat().entrySet()) {
                    builder.append(entry.getKey(), entry.getValue());
                }
            }

            builder.append("nodeIn", statistics.nodeIn)
                    .append("nodeOut", statistics.nodeOut)
                    .append("edgeForward", statistics.edgeForward)
                    .append("edgeBackward", statistics.edgeBackward);

            return builder.toString();
        }
    }
}
