package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.LinkView;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.Property;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("all")
public class ItemsRealmRealmProxy extends com.xapptree.ginger.model.ItemsRealm
    implements RealmObjectProxy, ItemsRealmRealmProxyInterface {

    static final class ItemsRealmColumnInfo extends ColumnInfo {
        long ItemIdIndex;
        long ItemNameIndex;
        long ItemUrlIndex;
        long ItemPriceIndex;
        long CategoryIdIndex;
        long StoreIdIndex;
        long AvailabilityIndex;
        long IsVegIndex;
        long IsDiscountIndex;
        long IsRecommendedIndex;
        long DiscountPercentIndex;

        ItemsRealmColumnInfo(SharedRealm realm, Table table) {
            super(11);
            this.ItemIdIndex = addColumnDetails(table, "ItemId", RealmFieldType.STRING);
            this.ItemNameIndex = addColumnDetails(table, "ItemName", RealmFieldType.STRING);
            this.ItemUrlIndex = addColumnDetails(table, "ItemUrl", RealmFieldType.STRING);
            this.ItemPriceIndex = addColumnDetails(table, "ItemPrice", RealmFieldType.DOUBLE);
            this.CategoryIdIndex = addColumnDetails(table, "CategoryId", RealmFieldType.STRING);
            this.StoreIdIndex = addColumnDetails(table, "StoreId", RealmFieldType.STRING);
            this.AvailabilityIndex = addColumnDetails(table, "Availability", RealmFieldType.BOOLEAN);
            this.IsVegIndex = addColumnDetails(table, "IsVeg", RealmFieldType.BOOLEAN);
            this.IsDiscountIndex = addColumnDetails(table, "IsDiscount", RealmFieldType.BOOLEAN);
            this.IsRecommendedIndex = addColumnDetails(table, "IsRecommended", RealmFieldType.BOOLEAN);
            this.DiscountPercentIndex = addColumnDetails(table, "DiscountPercent", RealmFieldType.INTEGER);
        }

        ItemsRealmColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new ItemsRealmColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final ItemsRealmColumnInfo src = (ItemsRealmColumnInfo) rawSrc;
            final ItemsRealmColumnInfo dst = (ItemsRealmColumnInfo) rawDst;
            dst.ItemIdIndex = src.ItemIdIndex;
            dst.ItemNameIndex = src.ItemNameIndex;
            dst.ItemUrlIndex = src.ItemUrlIndex;
            dst.ItemPriceIndex = src.ItemPriceIndex;
            dst.CategoryIdIndex = src.CategoryIdIndex;
            dst.StoreIdIndex = src.StoreIdIndex;
            dst.AvailabilityIndex = src.AvailabilityIndex;
            dst.IsVegIndex = src.IsVegIndex;
            dst.IsDiscountIndex = src.IsDiscountIndex;
            dst.IsRecommendedIndex = src.IsRecommendedIndex;
            dst.DiscountPercentIndex = src.DiscountPercentIndex;
        }
    }

    private ItemsRealmColumnInfo columnInfo;
    private ProxyState<com.xapptree.ginger.model.ItemsRealm> proxyState;
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("ItemId");
        fieldNames.add("ItemName");
        fieldNames.add("ItemUrl");
        fieldNames.add("ItemPrice");
        fieldNames.add("CategoryId");
        fieldNames.add("StoreId");
        fieldNames.add("Availability");
        fieldNames.add("IsVeg");
        fieldNames.add("IsDiscount");
        fieldNames.add("IsRecommended");
        fieldNames.add("DiscountPercent");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    ItemsRealmRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (ItemsRealmColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.xapptree.ginger.model.ItemsRealm>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$ItemId() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.ItemIdIndex);
    }

    @Override
    public void realmSet$ItemId(String value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'ItemId' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$ItemName() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.ItemNameIndex);
    }

    @Override
    public void realmSet$ItemName(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.ItemNameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.ItemNameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.ItemNameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.ItemNameIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$ItemUrl() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.ItemUrlIndex);
    }

    @Override
    public void realmSet$ItemUrl(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.ItemUrlIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.ItemUrlIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.ItemUrlIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.ItemUrlIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public double realmGet$ItemPrice() {
        proxyState.getRealm$realm().checkIfValid();
        return (double) proxyState.getRow$realm().getDouble(columnInfo.ItemPriceIndex);
    }

    @Override
    public void realmSet$ItemPrice(double value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setDouble(columnInfo.ItemPriceIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setDouble(columnInfo.ItemPriceIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$CategoryId() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.CategoryIdIndex);
    }

    @Override
    public void realmSet$CategoryId(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.CategoryIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.CategoryIdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.CategoryIdIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.CategoryIdIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$StoreId() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.StoreIdIndex);
    }

    @Override
    public void realmSet$StoreId(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.StoreIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.StoreIdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.StoreIdIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.StoreIdIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$Availability() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.AvailabilityIndex);
    }

    @Override
    public void realmSet$Availability(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.AvailabilityIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.AvailabilityIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$IsVeg() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.IsVegIndex);
    }

    @Override
    public void realmSet$IsVeg(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.IsVegIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.IsVegIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$IsDiscount() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.IsDiscountIndex);
    }

    @Override
    public void realmSet$IsDiscount(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.IsDiscountIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.IsDiscountIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$IsRecommended() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.IsRecommendedIndex);
    }

    @Override
    public void realmSet$IsRecommended(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.IsRecommendedIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.IsRecommendedIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$DiscountPercent() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.DiscountPercentIndex);
    }

    @Override
    public void realmSet$DiscountPercent(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.DiscountPercentIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.DiscountPercentIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("ItemsRealm");
        builder.addProperty("ItemId", RealmFieldType.STRING, Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED);
        builder.addProperty("ItemName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addProperty("ItemUrl", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addProperty("ItemPrice", RealmFieldType.DOUBLE, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addProperty("CategoryId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addProperty("StoreId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addProperty("Availability", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addProperty("IsVeg", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addProperty("IsDiscount", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addProperty("IsRecommended", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addProperty("DiscountPercent", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
         return expectedObjectSchemaInfo;
    }

    public static ItemsRealmColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (!sharedRealm.hasTable("class_ItemsRealm")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'ItemsRealm' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_ItemsRealm");
        final long columnCount = table.getColumnCount();
        if (columnCount != 11) {
            if (columnCount < 11) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 11 but was " + columnCount);
            }
            if (allowExtraColumns) {
                RealmLog.debug("Field count is more than expected - expected 11 but was %1$d", columnCount);
            } else {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 11 but was " + columnCount);
            }
        }
        Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
        for (long i = 0; i < columnCount; i++) {
            columnTypes.put(table.getColumnName(i), table.getColumnType(i));
        }

        final ItemsRealmColumnInfo columnInfo = new ItemsRealmColumnInfo(sharedRealm, table);

        if (!table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary key not defined for field 'ItemId' in existing Realm file. @PrimaryKey was added.");
        } else {
            if (table.getPrimaryKey() != columnInfo.ItemIdIndex) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key annotation definition was changed, from field " + table.getColumnName(table.getPrimaryKey()) + " to field ItemId");
            }
        }

        if (!columnTypes.containsKey("ItemId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'ItemId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("ItemId") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'ItemId' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.ItemIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(),"@PrimaryKey field 'ItemId' does not support null values in the existing Realm file. Migrate using RealmObjectSchema.setNullable(), or mark the field as @Required.");
        }
        if (!table.hasSearchIndex(table.getColumnIndex("ItemId"))) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Index not defined for field 'ItemId' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
        }
        if (!columnTypes.containsKey("ItemName")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'ItemName' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("ItemName") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'ItemName' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.ItemNameIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'ItemName' is required. Either set @Required to field 'ItemName' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("ItemUrl")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'ItemUrl' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("ItemUrl") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'ItemUrl' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.ItemUrlIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'ItemUrl' is required. Either set @Required to field 'ItemUrl' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("ItemPrice")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'ItemPrice' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("ItemPrice") != RealmFieldType.DOUBLE) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'double' for field 'ItemPrice' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.ItemPriceIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'ItemPrice' does support null values in the existing Realm file. Use corresponding boxed type for field 'ItemPrice' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("CategoryId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'CategoryId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("CategoryId") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'CategoryId' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.CategoryIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'CategoryId' is required. Either set @Required to field 'CategoryId' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("StoreId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'StoreId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("StoreId") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'StoreId' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.StoreIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'StoreId' is required. Either set @Required to field 'StoreId' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("Availability")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'Availability' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("Availability") != RealmFieldType.BOOLEAN) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'boolean' for field 'Availability' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.AvailabilityIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'Availability' does support null values in the existing Realm file. Use corresponding boxed type for field 'Availability' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("IsVeg")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'IsVeg' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("IsVeg") != RealmFieldType.BOOLEAN) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'boolean' for field 'IsVeg' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.IsVegIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'IsVeg' does support null values in the existing Realm file. Use corresponding boxed type for field 'IsVeg' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("IsDiscount")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'IsDiscount' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("IsDiscount") != RealmFieldType.BOOLEAN) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'boolean' for field 'IsDiscount' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.IsDiscountIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'IsDiscount' does support null values in the existing Realm file. Use corresponding boxed type for field 'IsDiscount' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("IsRecommended")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'IsRecommended' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("IsRecommended") != RealmFieldType.BOOLEAN) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'boolean' for field 'IsRecommended' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.IsRecommendedIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'IsRecommended' does support null values in the existing Realm file. Use corresponding boxed type for field 'IsRecommended' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("DiscountPercent")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'DiscountPercent' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("DiscountPercent") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'DiscountPercent' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.DiscountPercentIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'DiscountPercent' does support null values in the existing Realm file. Use corresponding boxed type for field 'DiscountPercent' or migrate using RealmObjectSchema.setNullable().");
        }

        return columnInfo;
    }

    public static String getTableName() {
        return "class_ItemsRealm";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.xapptree.ginger.model.ItemsRealm createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.xapptree.ginger.model.ItemsRealm obj = null;
        if (update) {
            Table table = realm.getTable(com.xapptree.ginger.model.ItemsRealm.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = Table.NO_MATCH;
            if (json.isNull("ItemId")) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, json.getString("ItemId"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(com.xapptree.ginger.model.ItemsRealm.class), false, Collections.<String> emptyList());
                    obj = new io.realm.ItemsRealmRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("ItemId")) {
                if (json.isNull("ItemId")) {
                    obj = (io.realm.ItemsRealmRealmProxy) realm.createObjectInternal(com.xapptree.ginger.model.ItemsRealm.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.ItemsRealmRealmProxy) realm.createObjectInternal(com.xapptree.ginger.model.ItemsRealm.class, json.getString("ItemId"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'ItemId'.");
            }
        }
        if (json.has("ItemName")) {
            if (json.isNull("ItemName")) {
                ((ItemsRealmRealmProxyInterface) obj).realmSet$ItemName(null);
            } else {
                ((ItemsRealmRealmProxyInterface) obj).realmSet$ItemName((String) json.getString("ItemName"));
            }
        }
        if (json.has("ItemUrl")) {
            if (json.isNull("ItemUrl")) {
                ((ItemsRealmRealmProxyInterface) obj).realmSet$ItemUrl(null);
            } else {
                ((ItemsRealmRealmProxyInterface) obj).realmSet$ItemUrl((String) json.getString("ItemUrl"));
            }
        }
        if (json.has("ItemPrice")) {
            if (json.isNull("ItemPrice")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'ItemPrice' to null.");
            } else {
                ((ItemsRealmRealmProxyInterface) obj).realmSet$ItemPrice((double) json.getDouble("ItemPrice"));
            }
        }
        if (json.has("CategoryId")) {
            if (json.isNull("CategoryId")) {
                ((ItemsRealmRealmProxyInterface) obj).realmSet$CategoryId(null);
            } else {
                ((ItemsRealmRealmProxyInterface) obj).realmSet$CategoryId((String) json.getString("CategoryId"));
            }
        }
        if (json.has("StoreId")) {
            if (json.isNull("StoreId")) {
                ((ItemsRealmRealmProxyInterface) obj).realmSet$StoreId(null);
            } else {
                ((ItemsRealmRealmProxyInterface) obj).realmSet$StoreId((String) json.getString("StoreId"));
            }
        }
        if (json.has("Availability")) {
            if (json.isNull("Availability")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'Availability' to null.");
            } else {
                ((ItemsRealmRealmProxyInterface) obj).realmSet$Availability((boolean) json.getBoolean("Availability"));
            }
        }
        if (json.has("IsVeg")) {
            if (json.isNull("IsVeg")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'IsVeg' to null.");
            } else {
                ((ItemsRealmRealmProxyInterface) obj).realmSet$IsVeg((boolean) json.getBoolean("IsVeg"));
            }
        }
        if (json.has("IsDiscount")) {
            if (json.isNull("IsDiscount")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'IsDiscount' to null.");
            } else {
                ((ItemsRealmRealmProxyInterface) obj).realmSet$IsDiscount((boolean) json.getBoolean("IsDiscount"));
            }
        }
        if (json.has("IsRecommended")) {
            if (json.isNull("IsRecommended")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'IsRecommended' to null.");
            } else {
                ((ItemsRealmRealmProxyInterface) obj).realmSet$IsRecommended((boolean) json.getBoolean("IsRecommended"));
            }
        }
        if (json.has("DiscountPercent")) {
            if (json.isNull("DiscountPercent")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'DiscountPercent' to null.");
            } else {
                ((ItemsRealmRealmProxyInterface) obj).realmSet$DiscountPercent((int) json.getInt("DiscountPercent"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.xapptree.ginger.model.ItemsRealm createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        com.xapptree.ginger.model.ItemsRealm obj = new com.xapptree.ginger.model.ItemsRealm();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("ItemId")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((ItemsRealmRealmProxyInterface) obj).realmSet$ItemId(null);
                } else {
                    ((ItemsRealmRealmProxyInterface) obj).realmSet$ItemId((String) reader.nextString());
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("ItemName")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((ItemsRealmRealmProxyInterface) obj).realmSet$ItemName(null);
                } else {
                    ((ItemsRealmRealmProxyInterface) obj).realmSet$ItemName((String) reader.nextString());
                }
            } else if (name.equals("ItemUrl")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((ItemsRealmRealmProxyInterface) obj).realmSet$ItemUrl(null);
                } else {
                    ((ItemsRealmRealmProxyInterface) obj).realmSet$ItemUrl((String) reader.nextString());
                }
            } else if (name.equals("ItemPrice")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'ItemPrice' to null.");
                } else {
                    ((ItemsRealmRealmProxyInterface) obj).realmSet$ItemPrice((double) reader.nextDouble());
                }
            } else if (name.equals("CategoryId")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((ItemsRealmRealmProxyInterface) obj).realmSet$CategoryId(null);
                } else {
                    ((ItemsRealmRealmProxyInterface) obj).realmSet$CategoryId((String) reader.nextString());
                }
            } else if (name.equals("StoreId")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((ItemsRealmRealmProxyInterface) obj).realmSet$StoreId(null);
                } else {
                    ((ItemsRealmRealmProxyInterface) obj).realmSet$StoreId((String) reader.nextString());
                }
            } else if (name.equals("Availability")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'Availability' to null.");
                } else {
                    ((ItemsRealmRealmProxyInterface) obj).realmSet$Availability((boolean) reader.nextBoolean());
                }
            } else if (name.equals("IsVeg")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'IsVeg' to null.");
                } else {
                    ((ItemsRealmRealmProxyInterface) obj).realmSet$IsVeg((boolean) reader.nextBoolean());
                }
            } else if (name.equals("IsDiscount")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'IsDiscount' to null.");
                } else {
                    ((ItemsRealmRealmProxyInterface) obj).realmSet$IsDiscount((boolean) reader.nextBoolean());
                }
            } else if (name.equals("IsRecommended")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'IsRecommended' to null.");
                } else {
                    ((ItemsRealmRealmProxyInterface) obj).realmSet$IsRecommended((boolean) reader.nextBoolean());
                }
            } else if (name.equals("DiscountPercent")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'DiscountPercent' to null.");
                } else {
                    ((ItemsRealmRealmProxyInterface) obj).realmSet$DiscountPercent((int) reader.nextInt());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'ItemId'.");
        }
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static com.xapptree.ginger.model.ItemsRealm copyOrUpdate(Realm realm, com.xapptree.ginger.model.ItemsRealm object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.xapptree.ginger.model.ItemsRealm) cachedRealmObject;
        }

        com.xapptree.ginger.model.ItemsRealm realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.xapptree.ginger.model.ItemsRealm.class);
            long pkColumnIndex = table.getPrimaryKey();
            String value = ((ItemsRealmRealmProxyInterface) object).realmGet$ItemId();
            long rowIndex = Table.NO_MATCH;
            if (value == null) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, value);
            }
            if (rowIndex != Table.NO_MATCH) {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(com.xapptree.ginger.model.ItemsRealm.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.ItemsRealmRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            } else {
                canUpdate = false;
            }
        }

        if (canUpdate) {
            return update(realm, realmObject, object, cache);
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static com.xapptree.ginger.model.ItemsRealm copy(Realm realm, com.xapptree.ginger.model.ItemsRealm newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.xapptree.ginger.model.ItemsRealm) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.xapptree.ginger.model.ItemsRealm realmObject = realm.createObjectInternal(com.xapptree.ginger.model.ItemsRealm.class, ((ItemsRealmRealmProxyInterface) newObject).realmGet$ItemId(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        ItemsRealmRealmProxyInterface realmObjectSource = (ItemsRealmRealmProxyInterface) newObject;
        ItemsRealmRealmProxyInterface realmObjectCopy = (ItemsRealmRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$ItemName(realmObjectSource.realmGet$ItemName());
        realmObjectCopy.realmSet$ItemUrl(realmObjectSource.realmGet$ItemUrl());
        realmObjectCopy.realmSet$ItemPrice(realmObjectSource.realmGet$ItemPrice());
        realmObjectCopy.realmSet$CategoryId(realmObjectSource.realmGet$CategoryId());
        realmObjectCopy.realmSet$StoreId(realmObjectSource.realmGet$StoreId());
        realmObjectCopy.realmSet$Availability(realmObjectSource.realmGet$Availability());
        realmObjectCopy.realmSet$IsVeg(realmObjectSource.realmGet$IsVeg());
        realmObjectCopy.realmSet$IsDiscount(realmObjectSource.realmGet$IsDiscount());
        realmObjectCopy.realmSet$IsRecommended(realmObjectSource.realmGet$IsRecommended());
        realmObjectCopy.realmSet$DiscountPercent(realmObjectSource.realmGet$DiscountPercent());
        return realmObject;
    }

    public static long insert(Realm realm, com.xapptree.ginger.model.ItemsRealm object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.xapptree.ginger.model.ItemsRealm.class);
        long tableNativePtr = table.getNativePtr();
        ItemsRealmColumnInfo columnInfo = (ItemsRealmColumnInfo) realm.schema.getColumnInfo(com.xapptree.ginger.model.ItemsRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        String primaryKeyValue = ((ItemsRealmRealmProxyInterface) object).realmGet$ItemId();
        long rowIndex = Table.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, primaryKeyValue);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$ItemName = ((ItemsRealmRealmProxyInterface) object).realmGet$ItemName();
        if (realmGet$ItemName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.ItemNameIndex, rowIndex, realmGet$ItemName, false);
        }
        String realmGet$ItemUrl = ((ItemsRealmRealmProxyInterface) object).realmGet$ItemUrl();
        if (realmGet$ItemUrl != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.ItemUrlIndex, rowIndex, realmGet$ItemUrl, false);
        }
        Table.nativeSetDouble(tableNativePtr, columnInfo.ItemPriceIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$ItemPrice(), false);
        String realmGet$CategoryId = ((ItemsRealmRealmProxyInterface) object).realmGet$CategoryId();
        if (realmGet$CategoryId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.CategoryIdIndex, rowIndex, realmGet$CategoryId, false);
        }
        String realmGet$StoreId = ((ItemsRealmRealmProxyInterface) object).realmGet$StoreId();
        if (realmGet$StoreId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.StoreIdIndex, rowIndex, realmGet$StoreId, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.AvailabilityIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$Availability(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.IsVegIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$IsVeg(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.IsDiscountIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$IsDiscount(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.IsRecommendedIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$IsRecommended(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.DiscountPercentIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$DiscountPercent(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.xapptree.ginger.model.ItemsRealm.class);
        long tableNativePtr = table.getNativePtr();
        ItemsRealmColumnInfo columnInfo = (ItemsRealmColumnInfo) realm.schema.getColumnInfo(com.xapptree.ginger.model.ItemsRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        com.xapptree.ginger.model.ItemsRealm object = null;
        while (objects.hasNext()) {
            object = (com.xapptree.ginger.model.ItemsRealm) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            String primaryKeyValue = ((ItemsRealmRealmProxyInterface) object).realmGet$ItemId();
            long rowIndex = Table.NO_MATCH;
            if (primaryKeyValue == null) {
                rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
            } else {
                rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, primaryKeyValue);
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$ItemName = ((ItemsRealmRealmProxyInterface) object).realmGet$ItemName();
            if (realmGet$ItemName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.ItemNameIndex, rowIndex, realmGet$ItemName, false);
            }
            String realmGet$ItemUrl = ((ItemsRealmRealmProxyInterface) object).realmGet$ItemUrl();
            if (realmGet$ItemUrl != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.ItemUrlIndex, rowIndex, realmGet$ItemUrl, false);
            }
            Table.nativeSetDouble(tableNativePtr, columnInfo.ItemPriceIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$ItemPrice(), false);
            String realmGet$CategoryId = ((ItemsRealmRealmProxyInterface) object).realmGet$CategoryId();
            if (realmGet$CategoryId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.CategoryIdIndex, rowIndex, realmGet$CategoryId, false);
            }
            String realmGet$StoreId = ((ItemsRealmRealmProxyInterface) object).realmGet$StoreId();
            if (realmGet$StoreId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.StoreIdIndex, rowIndex, realmGet$StoreId, false);
            }
            Table.nativeSetBoolean(tableNativePtr, columnInfo.AvailabilityIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$Availability(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.IsVegIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$IsVeg(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.IsDiscountIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$IsDiscount(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.IsRecommendedIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$IsRecommended(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.DiscountPercentIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$DiscountPercent(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.xapptree.ginger.model.ItemsRealm object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.xapptree.ginger.model.ItemsRealm.class);
        long tableNativePtr = table.getNativePtr();
        ItemsRealmColumnInfo columnInfo = (ItemsRealmColumnInfo) realm.schema.getColumnInfo(com.xapptree.ginger.model.ItemsRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        String primaryKeyValue = ((ItemsRealmRealmProxyInterface) object).realmGet$ItemId();
        long rowIndex = Table.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$ItemName = ((ItemsRealmRealmProxyInterface) object).realmGet$ItemName();
        if (realmGet$ItemName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.ItemNameIndex, rowIndex, realmGet$ItemName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.ItemNameIndex, rowIndex, false);
        }
        String realmGet$ItemUrl = ((ItemsRealmRealmProxyInterface) object).realmGet$ItemUrl();
        if (realmGet$ItemUrl != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.ItemUrlIndex, rowIndex, realmGet$ItemUrl, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.ItemUrlIndex, rowIndex, false);
        }
        Table.nativeSetDouble(tableNativePtr, columnInfo.ItemPriceIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$ItemPrice(), false);
        String realmGet$CategoryId = ((ItemsRealmRealmProxyInterface) object).realmGet$CategoryId();
        if (realmGet$CategoryId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.CategoryIdIndex, rowIndex, realmGet$CategoryId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.CategoryIdIndex, rowIndex, false);
        }
        String realmGet$StoreId = ((ItemsRealmRealmProxyInterface) object).realmGet$StoreId();
        if (realmGet$StoreId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.StoreIdIndex, rowIndex, realmGet$StoreId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.StoreIdIndex, rowIndex, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.AvailabilityIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$Availability(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.IsVegIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$IsVeg(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.IsDiscountIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$IsDiscount(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.IsRecommendedIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$IsRecommended(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.DiscountPercentIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$DiscountPercent(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.xapptree.ginger.model.ItemsRealm.class);
        long tableNativePtr = table.getNativePtr();
        ItemsRealmColumnInfo columnInfo = (ItemsRealmColumnInfo) realm.schema.getColumnInfo(com.xapptree.ginger.model.ItemsRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        com.xapptree.ginger.model.ItemsRealm object = null;
        while (objects.hasNext()) {
            object = (com.xapptree.ginger.model.ItemsRealm) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            String primaryKeyValue = ((ItemsRealmRealmProxyInterface) object).realmGet$ItemId();
            long rowIndex = Table.NO_MATCH;
            if (primaryKeyValue == null) {
                rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
            } else {
                rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$ItemName = ((ItemsRealmRealmProxyInterface) object).realmGet$ItemName();
            if (realmGet$ItemName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.ItemNameIndex, rowIndex, realmGet$ItemName, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.ItemNameIndex, rowIndex, false);
            }
            String realmGet$ItemUrl = ((ItemsRealmRealmProxyInterface) object).realmGet$ItemUrl();
            if (realmGet$ItemUrl != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.ItemUrlIndex, rowIndex, realmGet$ItemUrl, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.ItemUrlIndex, rowIndex, false);
            }
            Table.nativeSetDouble(tableNativePtr, columnInfo.ItemPriceIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$ItemPrice(), false);
            String realmGet$CategoryId = ((ItemsRealmRealmProxyInterface) object).realmGet$CategoryId();
            if (realmGet$CategoryId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.CategoryIdIndex, rowIndex, realmGet$CategoryId, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.CategoryIdIndex, rowIndex, false);
            }
            String realmGet$StoreId = ((ItemsRealmRealmProxyInterface) object).realmGet$StoreId();
            if (realmGet$StoreId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.StoreIdIndex, rowIndex, realmGet$StoreId, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.StoreIdIndex, rowIndex, false);
            }
            Table.nativeSetBoolean(tableNativePtr, columnInfo.AvailabilityIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$Availability(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.IsVegIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$IsVeg(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.IsDiscountIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$IsDiscount(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.IsRecommendedIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$IsRecommended(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.DiscountPercentIndex, rowIndex, ((ItemsRealmRealmProxyInterface) object).realmGet$DiscountPercent(), false);
        }
    }

    public static com.xapptree.ginger.model.ItemsRealm createDetachedCopy(com.xapptree.ginger.model.ItemsRealm realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.xapptree.ginger.model.ItemsRealm unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.xapptree.ginger.model.ItemsRealm();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.xapptree.ginger.model.ItemsRealm) cachedObject.object;
            }
            unmanagedObject = (com.xapptree.ginger.model.ItemsRealm) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        ItemsRealmRealmProxyInterface unmanagedCopy = (ItemsRealmRealmProxyInterface) unmanagedObject;
        ItemsRealmRealmProxyInterface realmSource = (ItemsRealmRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$ItemId(realmSource.realmGet$ItemId());
        unmanagedCopy.realmSet$ItemName(realmSource.realmGet$ItemName());
        unmanagedCopy.realmSet$ItemUrl(realmSource.realmGet$ItemUrl());
        unmanagedCopy.realmSet$ItemPrice(realmSource.realmGet$ItemPrice());
        unmanagedCopy.realmSet$CategoryId(realmSource.realmGet$CategoryId());
        unmanagedCopy.realmSet$StoreId(realmSource.realmGet$StoreId());
        unmanagedCopy.realmSet$Availability(realmSource.realmGet$Availability());
        unmanagedCopy.realmSet$IsVeg(realmSource.realmGet$IsVeg());
        unmanagedCopy.realmSet$IsDiscount(realmSource.realmGet$IsDiscount());
        unmanagedCopy.realmSet$IsRecommended(realmSource.realmGet$IsRecommended());
        unmanagedCopy.realmSet$DiscountPercent(realmSource.realmGet$DiscountPercent());
        return unmanagedObject;
    }

    static com.xapptree.ginger.model.ItemsRealm update(Realm realm, com.xapptree.ginger.model.ItemsRealm realmObject, com.xapptree.ginger.model.ItemsRealm newObject, Map<RealmModel, RealmObjectProxy> cache) {
        ItemsRealmRealmProxyInterface realmObjectTarget = (ItemsRealmRealmProxyInterface) realmObject;
        ItemsRealmRealmProxyInterface realmObjectSource = (ItemsRealmRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$ItemName(realmObjectSource.realmGet$ItemName());
        realmObjectTarget.realmSet$ItemUrl(realmObjectSource.realmGet$ItemUrl());
        realmObjectTarget.realmSet$ItemPrice(realmObjectSource.realmGet$ItemPrice());
        realmObjectTarget.realmSet$CategoryId(realmObjectSource.realmGet$CategoryId());
        realmObjectTarget.realmSet$StoreId(realmObjectSource.realmGet$StoreId());
        realmObjectTarget.realmSet$Availability(realmObjectSource.realmGet$Availability());
        realmObjectTarget.realmSet$IsVeg(realmObjectSource.realmGet$IsVeg());
        realmObjectTarget.realmSet$IsDiscount(realmObjectSource.realmGet$IsDiscount());
        realmObjectTarget.realmSet$IsRecommended(realmObjectSource.realmGet$IsRecommended());
        realmObjectTarget.realmSet$DiscountPercent(realmObjectSource.realmGet$DiscountPercent());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("ItemsRealm = proxy[");
        stringBuilder.append("{ItemId:");
        stringBuilder.append(realmGet$ItemId() != null ? realmGet$ItemId() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{ItemName:");
        stringBuilder.append(realmGet$ItemName() != null ? realmGet$ItemName() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{ItemUrl:");
        stringBuilder.append(realmGet$ItemUrl() != null ? realmGet$ItemUrl() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{ItemPrice:");
        stringBuilder.append(realmGet$ItemPrice());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{CategoryId:");
        stringBuilder.append(realmGet$CategoryId() != null ? realmGet$CategoryId() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{StoreId:");
        stringBuilder.append(realmGet$StoreId() != null ? realmGet$StoreId() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{Availability:");
        stringBuilder.append(realmGet$Availability());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{IsVeg:");
        stringBuilder.append(realmGet$IsVeg());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{IsDiscount:");
        stringBuilder.append(realmGet$IsDiscount());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{IsRecommended:");
        stringBuilder.append(realmGet$IsRecommended());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{DiscountPercent:");
        stringBuilder.append(realmGet$DiscountPercent());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemsRealmRealmProxy aItemsRealm = (ItemsRealmRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aItemsRealm.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aItemsRealm.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aItemsRealm.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
