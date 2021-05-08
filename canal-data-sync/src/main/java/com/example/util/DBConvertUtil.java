package com.example.util;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.mongodb.BasicDBObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;

import java.util.List;

/**
 * 数据转换类
 *
 */
@Slf4j
public class DBConvertUtil {

    /**
     * binlog行数据列转换成DBObject
     *
     * @param columns
     * @return
     */
    public static BasicDBObject columnToJson(List<CanalEntry.Column> columns) {
        BasicDBObject obj = new BasicDBObject();
        for (CanalEntry.Column column : columns) {
            Object value = dataTypeConvert(column.getMysqlType(), column.getValue());
            obj.put(column.getName(), value);
        }
        return obj;
    }

    /**
     * 数据类型转换
     *
     * @param mysqlType
     * @param value
     * @return
     */
    private static Object dataTypeConvert(String mysqlType, String value) {

        try {
            if (mysqlType.startsWith("int") || mysqlType.startsWith("tinyint") || mysqlType.startsWith("smallint") || mysqlType.startsWith("mediumint")) {
                //int(32)
                return StringUtils.isBlank(value) ? null : Integer.parseInt(value);
            } else if (mysqlType.startsWith("bigint")) {
                //int(64)
                return StringUtils.isBlank(value) ? null : Long.parseLong(value);
            } else if (mysqlType.startsWith("float") || mysqlType.startsWith("double")) {
                return StringUtils.isBlank(value) ? null : Double.parseDouble(value);
            } else if (mysqlType.startsWith("decimal")) {
                //小数精度为0时转换成long类型，否则转换为double类型
                int lenBegin = mysqlType.indexOf('(');
                int lenCenter = mysqlType.indexOf(',');
                int lenEnd = mysqlType.indexOf(')');
                if (lenBegin > 0 && lenEnd > 0 && lenCenter > 0) {
                    int length = Integer.parseInt(mysqlType.substring(lenCenter + 1, lenEnd));
                    if (length == 0) {
                        return StringUtils.isBlank(value) ? null : Long.parseLong(value);
                    }
                }
                return StringUtils.isBlank(value) ? null : Double.parseDouble(value);
            } else if (mysqlType.startsWith("datetime") || mysqlType.startsWith("timestamp")) {
                return StringUtils.isBlank(value) ? null : DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(value).toDate();
            } else if (mysqlType.equals("date")) {
                return StringUtils.isBlank(value) ? null : DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(value).toDate();
            } else if (mysqlType.startsWith("varchar") || mysqlType.startsWith("char")) {
                //设置默认空串
                return value == null ? "" : value;
            } else {
                log.error("unknown data type :[{}]-[{}]", mysqlType, value);
            }
        } catch (Exception e) {
            log.error("data type convert error ", e);
        }
        return value;
    }
}
