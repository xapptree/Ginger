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
public class LineItemRealmRealmProxy extends com.xapptree.ginger.model.LineItemRealm
    implements RealmObjectProxy, LineItemRealmRealmProxyInterface {

    static final class LineItemRealmColumnInfo extends ColumnInfo {
        long IdIndex;
        long ItemIdIndex;
        long ItemNameIndex;
        long ItemPriceIndex;
        long ItemQuantityIndex;
        long ItemTotalIndex;
        long IsDiscountIndex;
        long IsVegIndex;
        long DiscountPercentIndex;
        long Extended_TotalIndex;

        LineItemRealmColumnInfo(SharedRealm realm, Table table) {
            super(10);
            this.IdIndex = addColumnDetails(table, "Id", RealmFieldType.INTEGER);
            this.ItemIdIndex = addColumnDetails(table, "ItemId", RealmFieldType.STRING);
            this.ItemNameIndex = addColumnDetails(table, "ItemName", RealmFieldType.STRING);
            this.ItemPriceIndex = addColumnDetails(table, "ItemPrice", RealmFieldType.DOUBLE);
            this.ItemQuantityIndex = addColumnDetails(table, "ItemQuantity", RealmFieldType.INTEGER);
            this.ItemTotalIndex = addColumnDetails(table, "ItemTotal", RealmFieldType.DOUBLE);
            this.IsDiscountIndex = addColumnDetails(table, "IsDiscount", RealmFieldType.BOOLEAN);
            this.IsVegIndex = addColumnDetails(table, "IsVeg", RealmFieldType.BOOLEAN);
            this.DiscountPercentIndex = addColumnDetails(table, "DiscountPercent", RealmFieldType.INTEGER);
            this.Extended_TotalIndex = addColumnDetails(table, "Extended_Total", RealmFieldType.DOUBLE);
        }

        LineItemRealmColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new LineItemRealmColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final LineItemRealmColumnInfo src = (LineItemRealmColumnInfo) rawSrc;
            final LineItemRealmColumnInfo dst = (LineItemRealmColumnInfo) rawDst;
            dst.IdIndex = src.IdIndex;
            dst.ItemIdIndex = src.ItemIdIndex;
            dst.ItemNameIndex = src.ItemNameIndex;
            dst.ItemPriceIndex = src.ItemPriceIndex;
            dst.ItemQuantityIndex = src.ItemQuantityIndex;
            dst.ItemTotalIndex = src.ItemTotalIndex;
            dst.IsDiscountIndex = src.IsDiscountIndex;
            dst.IsVegIndex = src.IsVegIndex;
            dst.DiscountPercentIndex = src.DiscountPercentIndex;
            dst.Extended_TotalIndex = src.Extended_TotalIndex;
        }
    }

    private LineItemRealmColumnInfo columnInfo;
    private ProxyState<com.xapptree.ginger.model.LineItemRealm> proxyState;
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("Id");
        fieldNames.add("ItemId");
        fieldNames.add("ItemName");
        fieldNames.add("ItemPrice");
        fieldNames.add("ItemQuantity");
        fieldNames.add("ItemTotal");
        fieldNames.add("IsDiscount");
        fieldNames.add("IsVeg");
        fieldNames.add("DiscountPercent");
        fieldNames.add("Extended_Total");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    LineItemRealmRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (LineItemRealmColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.xapptree.ginger.model.LineItemRealm>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$Id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.IdIndex);
    }

    @Override
    public void realmSet$Id(int value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'Id' cannot be changed after object was created.");
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
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.ItemIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.ItemIdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.ItemIdIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.ItemIdIndex, value);
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
    public int realmGet$ItemQuantity() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.ItemQuantityIndex);
    }

    @Override
    public void realmSet$ItemQuantity(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.ItemQuantityIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.ItemQuantityIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public double realmGet$ItemTotal() {
        proxyState.getRealm$realm().checkIfValid();
        return (double) proxyState.getRow$realm().getDouble(columnInfo.ItemTotalIndex);
    }

    @Override
    public void realmSet$ItemTotal(double value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setDouble(columnInfo.ItemTotalIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setDouble(columnInfo.ItemTotalIndex, value);
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

    @Override
    @SuppressWarnings("cast")
    public double realmGet$Extended_Total() {
        proxyState.getRealm$realm().checkIfValid();
        return (double) proxyState.getRow$realm().getDouble(columnInfo.Extended_TotalIndex);
    }

    @Override
    public void realmSet$Extended_Total(double value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setDouble(columnInfo.Extended_TotalIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setDouble(columnInfo.Extended_TotalIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("LineItemRealm");
        builder.addProperty("Id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addProperty("ItemId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addProperty("ItemName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addProperty("ItemPrice", RealmFieldType.DOUBLE, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addProperty("ItemQuantity", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addProperty("ItemTotal", RealmFieldType.DOUBLE, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addProperty("IsDiscount", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addProperty("IsVeg", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addProperty("DiscountPercent", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addProperty("Extended_Total", RealmFieldType.DOUBLE, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
         return expectedObjectSchemaInfo;
    }

    public static LineItemRealmColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (!sharedRealm.hasTable("class_LineItemRealm")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'LineItemRealm' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_LineItemRealm");
        final long columnCount = table.getColumnCount();
        if (columnCount != 10) {
            if (columnCount < 10) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 10 but was " + columnCount);
            }
            if (allowExtraColumns) {
                RealmLog.debug("Field count is more than expected - expected 10 but was %1$d", columnCount);
            } else {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 10 but was " + columnCount);
            }
        }
        Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
        for (long i = 0; i < columnCount; i++) {
            columnTypes.put(table.getColumnName(i), table.getColumnType(i));
        }

        final LineItemRealmColumnInfo columnInfo = new LineItemRealmColumnInfo(sharedRealm, table);

        if (!table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary key not defined for field 'Id' in existing Realm file. @PrimaryKey was added.");
        } else {
            if (table.getPrimaryKey() != columnInfo.IdIndex) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key annotation definition was changed, from field " + table.getColumnName(table.getPrimaryKey()) + " to field Id");
            }
        }

        if (!columnTypes.containsKey("Id")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'Id' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("Id") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'Id' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.IdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'Id' does support null values in the existing Realm file. Use corresponding boxed type for field 'Id' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!table.hasSearchIndex(table.getColumnIndex("Id"))) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Index not defined for field 'Id' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
        }
        if (!columnTypes.containsKey("ItemId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'ItemId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("ItemId") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'ItemId' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.ItemIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'ItemId' is required. Either set @Required to field 'ItemId' or migrate using RealmObjectSchema.setNullable().");
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
        if (!columnTypes.containsKey("ItemPrice")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'ItemPrice' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("ItemPrice") != RealmFieldType.DOUBLE) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'double' for field 'ItemPrice' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.ItemPriceIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'ItemPrice' does support null values in the existing Realm file. Use corresponding boxed type for field 'ItemPrice' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("ItemQuantity")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'ItemQuantity' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("ItemQuantity") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'ItemQuantity' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.ItemQuantityIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'ItemQuantity' does support null values in the existing Realm file. Use corresponding boxed type for field 'ItemQuantity' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("ItemTotal")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'ItemTotal' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("ItemTotal") != RealmFieldType.DOUBLE) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'double' for field 'ItemTotal' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.ItemTotalIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'ItemTotal' does support null values in the existing Realm file. Use corresponding boxed type for field 'ItemTotal' or migrate using RealmObjectSchema.setNullable().");
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
        if (!columnTypes.containsKey("IsVeg")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'IsVeg' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("IsVeg") != RealmFieldType.BOOLEAN) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'boolean' for field 'IsVeg' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.IsVegIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'IsVeg' does support null values in the existing Realm file. Use corresponding boxed type for field 'IsVeg' or migrate using RealmObjectSchema.setNullable().");
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
        if (!columnTypes.containsKey("Extended_Total")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'Extended_Total' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("Extended_Total") != RealmFieldType.DOUBLE) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'double' for field 'Extended_Total' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.Extended_TotalIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'Extended_Total' does support null values in the existing Realm file. Use corresponding boxed type for field 'Extended_Total' or migrate using RealmObjectSchema.setNullable().");
        }

        return columnInfo;
    }

    public static String getTableName() {
        return "class_LineItemRealm";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.xapptree.ginger.model.LineItemRealm createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.xapptree.ginger.model.LineItemRealm obj = null;
        if (update) {
            Table table = realm.getTable(com.xapptree.ginger.model.LineItemRealm.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("Id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("Id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(com.xapptree.ginger.model.LineItemRealm.class), false, Collections.<String> emptyList());
                    obj = new io.realm.LineItemRealmRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("Id")) {
                if (json.isNull("Id")) {
                    obj = (io.realm.LineItemRealmRealmProxy) realm.createObjectInternal(com.xapptree.ginger.model.LineItemRealm.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.LineItemRealmRealmProxy) realm.createObjectInternal(com.xapptree.ginger.model.LineItemRealm.class, json.getInt("Id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'Id'.");
            }
        }
        if (json.has("ItemId")) {
            if (json.isNull("ItemId")) {
                ((LineItemRealmRealmProxyInterface) obj).realmSet$ItemId(null);
            } else {
                ((LineItemRealmRealmProxyInterface) obj).realmSet$ItemId((String) json.getString("ItemId"));
            }
        }
        if (json.has("ItemName")) {
            if (json.isNull("ItemName")) {
                ((LineItemRealmRealmProxyInterface) obj).realmSet$ItemName(null);
            } else {
                ((LineItemRealmRealmProxyInterface) obj).realmSet$ItemName((String) json.getString("ItemName"));
            }
        }
        if (json.has("ItemPrice")) {
            if (json.isNull("ItemPrice")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'ItemPrice' to null.");
            } else {
                ((LineItemRealmRealmProxyInterface) obj).realmSet$ItemPrice((double) json.getDouble("ItemPrice"));
            }
        }
        if (json.has("ItemQuantity")) {
            if (json.isNull("ItemQuantity")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'ItemQuantity' to null.");
            } else {
                ((LineItemRealmRealmProxyInterface) obj).realmSet$ItemQuantity((int) json.getInt("ItemQuantity"));
            }
        }
        if (json.has("ItemTotal")) {
            if (json.isNull("ItemTotal")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'ItemTotal' to null.");
            } else {
                ((LineItemRealmRealmProxyInterface) obj).realmSet$ItemTotal((double) json.getDouble("ItemTotal"));
            }
        }
        if (json.has("IsDiscount")) {
            if (json.isNull("IsDiscount")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'IsDiscount' to null.");
            } else {
                ((LineItemRealmRealmProxyInterface) obj).realmSet$IsDiscount((boolean) json.getBoolean("IsDiscount"));
            }
        }
        if (json.has("IsVeg")) {
            if (json.isNull("IsVeg")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'IsVeg' to null.");
            } else {
                ((LineItemRealmRealmProxyInterface) obj).realmSet$IsVeg((boolean) json.getBoolean("IsVeg"));
            }
        }
        if (json.has("DiscountPercent")) {
            if (json.isNull("DiscountPercent")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'DiscountPercent' to null.");
            } else {
                ((LineItemRealmRealmProxyInterface) obj).realmSet$DiscountPercent((int) json.getInt("DiscountPercent"));
            }
        }
        if (json.has("Extended_Total")) {
            if (json.isNull("Extended_Total")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'Extended_Total' to null.");
            } else {
                ((LineItemRealmRealmProxyInterface) obj).realmSet$Extended_Total((double) json.getDouble("Extended_Total"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.xapptree.ginger.model.LineItemRealm createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        com.xapptree.ginger.model.LineItemRealm obj = new com.xapptree.ginger.model.LineItemRealm();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("Id")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'Id' to null.");
                } else {
                    ((LineItemRealmRealmProxyInterface) obj).realmSet$Id((int) reader.nextInt());
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("ItemId")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((LineItemRealmRealmProxyInterface) obj).realmSet$ItemId(null);
                } else {
                    ((LineItemRealmRealmProxyInterface) obj).realmSet$ItemId((String) reader.nextString());
                }
            } else if (name.equals("ItemName")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((LineItemRealmRealmProxyInterface) obj).realmSet$ItemName(null);
                } else {
                    ((LineItemRealmRealmProxyInterface) obj).realmSet$ItemName((String) reader.nextString());
                }
            } else if (name.equals("ItemPrice")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'ItemPrice' to null.");
                } else {
                    ((LineItemRealmRealmProxyInterface) obj).realmSet$ItemPrice((double) reader.nextDouble());
                }
            } else if (name.equals("ItemQuantity")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'ItemQuantity' to null.");
                } else {
                    ((LineItemRealmRealmProxyInterface) obj).realmSet$ItemQuantity((int) reader.nextInt());
                }
            } else if (name.equals("ItemTotal")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'ItemTotal' to null.");
                } else {
                    ((LineItemRealmRealmProxyInterface) obj).realmSet$ItemTotal((double) reader.nextDouble());
                }
            } else if (name.equals("IsDiscount")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'IsDiscount' to null.");
                } else {
                    ((LineItemRealmRealmProxyInterface) obj).realmSet$IsDiscount((boolean) reader.nextBoolean());
                }
            } else if (name.equals("IsVeg")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'IsVeg' to null.");
                } else {
                    ((LineItemRealmRealmProxyInterface) obj).realmSet$IsVeg((boolean) reader.nextBoolean());
                }
            } else if (name.equals("DiscountPercent")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'DiscountPercent' to null.");
                } else {
                    ((LineItemRealmRealmProxyInterface) obj).realmSet$DiscountPercent((int) reader.nextInt());
                }
            } else if (name.equals("Extended_Total")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'Extended_Total' to null.");
                } else {
                    ((LineItemRealmRealmProxyInterface) obj).realmSet$Extended_Total((double) reader.nextDouble());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'Id'.");
        }
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static com.xapptree.ginger.model.LineItemRealm copyOrUpdate(Realm realm, com.xapptree.ginger.model.LineItemRealm object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.xapptree.ginger.model.LineItemRealm) cachedRealmObject;
        }

        com.xapptree.ginger.model.LineItemRealm realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.xapptree.ginger.model.LineItemRealm.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = table.findFirstLong(pkColumnIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$Id());
            if (rowIndex != Table.NO_MATCH) {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(com.xapptree.ginger.model.LineItemRealm.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.LineItemRealmRealmProxy();
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

    public static com.xapptree.ginger.model.LineItemRealm copy(Realm realm, com.xapptree.ginger.model.LineItemRealm newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.xapptree.ginger.model.LineItemRealm) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.xapptree.ginger.model.LineItemRealm realmObject = realm.createObjectInternal(com.xapptree.ginger.model.LineItemRealm.class, ((LineItemRealmRealmProxyInterface) newObject).realmGet$Id(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        LineItemRealmRealmProxyInterface realmObjectSource = (LineItemRealmRealmProxyInterface) newObject;
        LineItemRealmRealmProxyInterface realmObjectCopy = (LineItemRealmRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$ItemId(realmObjectSource.realmGet$ItemId());
        realmObjectCopy.realmSet$ItemName(realmObjectSource.realmGet$ItemName());
        realmObjectCopy.realmSet$ItemPrice(realmObjectSource.realmGet$ItemPrice());
        realmObjectCopy.realmSet$ItemQuantity(realmObjectSource.realmGet$ItemQuantity());
        realmObjectCopy.realmSet$ItemTotal(realmObjectSource.realmGet$ItemTotal());
        realmObjectCopy.realmSet$IsDiscount(realmObjectSource.realmGet$IsDiscount());
        realmObjectCopy.realmSet$IsVeg(realmObjectSource.realmGet$IsVeg());
        realmObjectCopy.realmSet$DiscountPercent(realmObjectSource.realmGet$DiscountPercent());
        realmObjectCopy.realmSet$Extended_Total(realmObjectSource.realmGet$Extended_Total());
        return realmObject;
    }

    public static long insert(Realm realm, com.xapptree.ginger.model.LineItemRealm object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.xapptree.ginger.model.LineItemRealm.class);
        long tableNativePtr = table.getNativePtr();
        LineItemRealmColumnInfo columnInfo = (LineItemRealmColumnInfo) realm.schema.getColumnInfo(com.xapptree.ginger.model.LineItemRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((LineItemRealmRealmProxyInterface) object).realmGet$Id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$Id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, ((LineItemRealmRealmProxyInterface) object).realmGet$Id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$ItemId = ((LineItemRealmRealmProxyInterface) object).realmGet$ItemId();
        if (realmGet$ItemId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.ItemIdIndex, rowIndex, realmGet$ItemId, false);
        }
        String realmGet$ItemName = ((LineItemRealmRealmProxyInterface) object).realmGet$ItemName();
        if (realmGet$ItemName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.ItemNameIndex, rowIndex, realmGet$ItemName, false);
        }
        Table.nativeSetDouble(tableNativePtr, columnInfo.ItemPriceIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$ItemPrice(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.ItemQuantityIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$ItemQuantity(), false);
        Table.nativeSetDouble(tableNativePtr, columnInfo.ItemTotalIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$ItemTotal(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.IsDiscountIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$IsDiscount(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.IsVegIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$IsVeg(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.DiscountPercentIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$DiscountPercent(), false);
        Table.nativeSetDouble(tableNativePtr, columnInfo.Extended_TotalIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$Extended_Total(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.xapptree.ginger.model.LineItemRealm.class);
        long tableNativePtr = table.getNativePtr();
        LineItemRealmColumnInfo columnInfo = (LineItemRealmColumnInfo) realm.schema.getColumnInfo(com.xapptree.ginger.model.LineItemRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        com.xapptree.ginger.model.LineItemRealm object = null;
        while (objects.hasNext()) {
            object = (com.xapptree.ginger.model.LineItemRealm) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((LineItemRealmRealmProxyInterface) object).realmGet$Id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$Id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, ((LineItemRealmRealmProxyInterface) object).realmGet$Id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$ItemId = ((LineItemRealmRealmProxyInterface) object).realmGet$ItemId();
            if (realmGet$ItemId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.ItemIdIndex, rowIndex, realmGet$ItemId, false);
            }
            String realmGet$ItemName = ((LineItemRealmRealmProxyInterface) object).realmGet$ItemName();
            if (realmGet$ItemName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.ItemNameIndex, rowIndex, realmGet$ItemName, false);
            }
            Table.nativeSetDouble(tableNativePtr, columnInfo.ItemPriceIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$ItemPrice(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.ItemQuantityIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$ItemQuantity(), false);
            Table.nativeSetDouble(tableNativePtr, columnInfo.ItemTotalIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$ItemTotal(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.IsDiscountIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$IsDiscount(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.IsVegIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$IsVeg(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.DiscountPercentIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$DiscountPercent(), false);
            Table.nativeSetDouble(tableNativePtr, columnInfo.Extended_TotalIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$Extended_Total(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.xapptree.ginger.model.LineItemRealm object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.xapptree.ginger.model.LineItemRealm.class);
        long tableNativePtr = table.getNativePtr();
        LineItemRealmColumnInfo columnInfo = (LineItemRealmColumnInfo) realm.schema.getColumnInfo(com.xapptree.ginger.model.LineItemRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((LineItemRealmRealmProxyInterface) object).realmGet$Id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$Id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, ((LineItemRealmRealmProxyInterface) object).realmGet$Id());
        }
        cache.put(object, rowIndex);
        String realmGet$ItemId = ((LineItemRealmRealmProxyInterface) object).realmGet$ItemId();
        if (realmGet$ItemId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.ItemIdIndex, rowIndex, realmGet$ItemId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.ItemIdIndex, rowIndex, false);
        }
        String realmGet$ItemName = ((LineItemRealmRealmProxyInterface) object).realmGet$ItemName();
        if (realmGet$ItemName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.ItemNameIndex, rowIndex, realmGet$ItemName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.ItemNameIndex, rowIndex, false);
        }
        Table.nativeSetDouble(tableNativePtr, columnInfo.ItemPriceIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$ItemPrice(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.ItemQuantityIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$ItemQuantity(), false);
        Table.nativeSetDouble(tableNativePtr, columnInfo.ItemTotalIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$ItemTotal(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.IsDiscountIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$IsDiscount(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.IsVegIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$IsVeg(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.DiscountPercentIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$DiscountPercent(), false);
        Table.nativeSetDouble(tableNativePtr, columnInfo.Extended_TotalIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$Extended_Total(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.xapptree.ginger.model.LineItemRealm.class);
        long tableNativePtr = table.getNativePtr();
        LineItemRealmColumnInfo columnInfo = (LineItemRealmColumnInfo) realm.schema.getColumnInfo(com.xapptree.ginger.model.LineItemRealm.class);
        long pkColumnIndex = table.getPrimaryKey();
        com.xapptree.ginger.model.LineItemRealm object = null;
        while (objects.hasNext()) {
            object = (com.xapptree.ginger.model.LineItemRealm) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((LineItemRealmRealmProxyInterface) object).realmGet$Id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$Id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, ((LineItemRealmRealmProxyInterface) object).realmGet$Id());
            }
            cache.put(object, rowIndex);
            String realmGet$ItemId = ((LineItemRealmRealmProxyInterface) object).realmGet$ItemId();
            if (realmGet$ItemId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.ItemIdIndex, rowIndex, realmGet$ItemId, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.ItemIdIndex, rowIndex, false);
            }
            String realmGet$ItemName = ((LineItemRealmRealmProxyInterface) object).realmGet$ItemName();
            if (realmGet$ItemName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.ItemNameIndex, rowIndex, realmGet$ItemName, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.ItemNameIndex, rowIndex, false);
            }
            Table.nativeSetDouble(tableNativePtr, columnInfo.ItemPriceIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$ItemPrice(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.ItemQuantityIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$ItemQuantity(), false);
            Table.nativeSetDouble(tableNativePtr, columnInfo.ItemTotalIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$ItemTotal(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.IsDiscountIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$IsDiscount(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.IsVegIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$IsVeg(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.DiscountPercentIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$DiscountPercent(), false);
            Table.nativeSetDouble(tableNativePtr, columnInfo.Extended_TotalIndex, rowIndex, ((LineItemRealmRealmProxyInterface) object).realmGet$Extended_Total(), false);
        }
    }

    public static com.xapptree.ginger.model.LineItemRealm createDetachedCopy(com.xapptree.ginger.model.LineItemRealm realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.xapptree.ginger.model.LineItemRealm unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.xapptree.ginger.model.LineItemRealm();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.xapptree.ginger.model.LineItemRealm) cachedObject.object;
            }
            unmanagedObject = (com.xapptree.ginger.model.LineItemRealm) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        LineItemRealmRealmProxyInterface unmanagedCopy = (LineItemRealmRealmProxyInterface) unmanagedObject;
        LineItemRealmRealmProxyInterface realmSource = (LineItemRealmRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$Id(realmSource.realmGet$Id());
        unmanagedCopy.realmSet$ItemId(realmSource.realmGet$ItemId());
        unmanagedCopy.realmSet$ItemName(realmSource.realmGet$ItemName());
        unmanagedCopy.realmSet$ItemPrice(realmSource.realmGet$ItemPrice());
        unmanagedCopy.realmSet$ItemQuantity(realmSource.realmGet$ItemQuantity());
        unmanagedCopy.realmSet$ItemTotal(realmSource.realmGet$ItemTotal());
        unmanagedCopy.realmSet$IsDiscount(realmSource.realmGet$IsDiscount());
        unmanagedCopy.realmSet$IsVeg(realmSource.realmGet$IsVeg());
        unmanagedCopy.realmSet$DiscountPercent(realmSource.realmGet$DiscountPercent());
        unmanagedCopy.realmSet$Extended_Total(realmSource.realmGet$Extended_Total());
        return unmanagedObject;
    }

    static com.xapptree.ginger.model.LineItemRealm update(Realm realm, com.xapptree.ginger.model.LineItemRealm realmObject, com.xapptree.ginger.model.LineItemRealm newObject, Map<RealmModel, RealmObjectProxy> cache) {
        LineItemRealmRealmProxyInterface realmObjectTarget = (LineItemRealmRealmProxyInterface) realmObject;
        LineItemRealmRealmProxyInterface realmObjectSource = (LineItemRealmRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$ItemId(realmObjectSource.realmGet$ItemId());
        realmObjectTarget.realmSet$ItemName(realmObjectSource.realmGet$ItemName());
        realmObjectTarget.realmSet$ItemPrice(realmObjectSource.realmGet$ItemPrice());
        realmObjectTarget.realmSet$ItemQuantity(realmObjectSource.realmGet$ItemQuantity());
        realmObjectTarget.realmSet$ItemTotal(realmObjectSource.realmGet$ItemTotal());
        realmObjectTarget.realmSet$IsDiscount(realmObjectSource.realmGet$IsDiscount());
        realmObjectTarget.realmSet$IsVeg(realmObjectSource.realmGet$IsVeg());
        realmObjectTarget.realmSet$DiscountPercent(realmObjectSource.realmGet$DiscountPercent());
        realmObjectTarget.realmSet$Extended_Total(realmObjectSource.realmGet$Extended_Total());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("LineItemRealm = proxy[");
        stringBuilder.append("{Id:");
        stringBuilder.append(realmGet$Id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{ItemId:");
        stringBuilder.append(realmGet$ItemId() != null ? realmGet$ItemId() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{ItemName:");
        stringBuilder.append(realmGet$ItemName() != null ? realmGet$ItemName() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{ItemPrice:");
        stringBuilder.append(realmGet$ItemPrice());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{ItemQuantity:");
        stringBuilder.append(realmGet$ItemQuantity());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{ItemTotal:");
        stringBuilder.append(realmGet$ItemTotal());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{IsDiscount:");
        stringBuilder.append(realmGet$IsDiscount());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{IsVeg:");
        stringBuilder.append(realmGet$IsVeg());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{DiscountPercent:");
        stringBuilder.append(realmGet$DiscountPercent());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{Extended_Total:");
        stringBuilder.append(realmGet$Extended_Total());
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
        LineItemRealmRealmProxy aLineItemRealm = (LineItemRealmRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aLineItemRealm.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aLineItemRealm.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aLineItemRealm.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
