package com.travelnestpro.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DatabaseConstraintCleanup implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseConstraintCleanup(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        dropUnintendedUniqueTextIndexes();
    }

    private void dropUnintendedUniqueTextIndexes() {
        List<UniqueIndex> indexes = jdbcTemplate.query("""
                SELECT TABLE_NAME, INDEX_NAME
                FROM INFORMATION_SCHEMA.STATISTICS
                WHERE TABLE_SCHEMA = DATABASE()
                  AND TABLE_NAME IN ('homestays', 'property', 'properties')
                  AND COLUMN_NAME IN ('title', 'name')
                  AND NON_UNIQUE = 0
                  AND INDEX_NAME <> 'PRIMARY'
                GROUP BY TABLE_NAME, INDEX_NAME
                """, (rs, rowNum) -> new UniqueIndex(rs.getString("TABLE_NAME"), rs.getString("INDEX_NAME")));

        for (UniqueIndex index : indexes) {
            jdbcTemplate.execute("ALTER TABLE `" + index.tableName() + "` DROP INDEX `" + index.indexName() + "`");
        }
    }

    private record UniqueIndex(String tableName, String indexName) {
    }
}
