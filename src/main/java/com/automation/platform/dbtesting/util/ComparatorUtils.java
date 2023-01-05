package com.automation.platform.dbtesting.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ComparatorUtils {
    private static final int rowLength = 129;
    private static final char dash = '-';
    public static <T> String compareEntities(List<T> actualList,
                                       List<T> expectedList, List<String> ignoreField,
                                       Class<T> tClass) {
        try {
            assertThat(actualList)
                    .usingElementComparatorIgnoringFields(ignoreField.toArray(new String[ignoreField.size()]))
                    .isEqualTo(expectedList);
        } catch (AssertionError error) {
            return getMessage(actualList, expectedList, tClass);
        }
        return null;
    }

    private static <T> String getMessage(List<T> actualList, List<T> expectedList, Class<T> targetClass) {
        Javers javers = JaversBuilder.javers().registerValueObject(targetClass).build();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < actualList.size(); i++) {
            Diff diff = javers.compare(expectedList.get(i), actualList.get(i));
            int row = i +1;
            sb = print(sb, row, actualList.get(i), expectedList.get(i), diff);
        }
        return sb.toString();
    }

    private static StringBuilder print(StringBuilder sb, int row, Object actual, Object expected, Diff diff) {
        List<ValueChange> changes = diff.getChangesByType(ValueChange.class);
        if (!changes.isEmpty()) {
            sb.append(System.lineSeparator());
            sb.append("Database Record : ").append(row).append(" : ").append(ReflectionToStringBuilder.toString(actual));
            sb.append(System.lineSeparator());
            sb.append("CSV Record : ").append(row).append(" : ").append(ReflectionToStringBuilder.toString(expected));
            if (CollectionUtils.isEmpty(changes)) {
                sb.append("\nIncorrect Comparator used. Please check the actual and expected list");
                sb.append(System.lineSeparator());
            } else {
                createHeader(sb);
                for (ValueChange ch : changes) {
                    sb.append(String.format("|%-30s|", ch.getPropertyName()));
                    sb.append(String.format("%-50s|", ch.getRight()));
                    sb.append(String.format("%-45s|", ch.getLeft()));
                    sb.append("\n");
                    sb.append(System.lineSeparator());
                }
                sb.append(StringUtils.repeat(dash, rowLength));
            }
        }
        return sb;
    }

    private static StringBuilder createHeader(StringBuilder sb) {
        sb.append(System.lineSeparator());
        sb.append("Field Level Differences :\n");
        sb.append(StringUtils.repeat(dash, rowLength));
        sb.append(System.lineSeparator());
        sb.append(String.format("|%-30s|%-50s|%-45s|\n", "Property Name", "DATABASE VALUE", "CSV VALUE"));
        sb.append(StringUtils.repeat(dash, rowLength));
        return sb;
    }
}
