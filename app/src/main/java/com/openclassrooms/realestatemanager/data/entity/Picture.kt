package com.openclassrooms.realestatemanager.data.entity

import androidx.room.*
import com.openclassrooms.realestatemanager.utils.PICTURE_TABLE_NAME

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */

@Entity(
        tableName = PICTURE_TABLE_NAME,
        indices = [Index(value = ["id_property"])],
        foreignKeys = [
            ForeignKey(
                    entity = Property::class,
                    parentColumns = ["property_id"],
                    childColumns = ["id_property"],
                    onDelete = ForeignKey.CASCADE
            )
        ]
)
data class Picture(
        @PrimaryKey @ColumnInfo(name = "picture_id") var id: String = "",
        var url: String = "",
        @ColumnInfo(name = "thumbnail_url") var thumbnailUrl: String? = null,
        @ColumnInfo(name = "server_url") var serverUrl: String? = null,
        @ColumnInfo(name = "id_property") var property: String? = null,
        @ColumnInfo(name = "picture_description") var description: String = "",
        @ColumnInfo(name = "order_number") var orderNumber: Int? = null
)