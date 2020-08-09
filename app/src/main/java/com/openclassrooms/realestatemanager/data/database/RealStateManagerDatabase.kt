package com.openclassrooms.realestatemanager.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.openclassrooms.realestatemanager.data.database.dao.*
import com.openclassrooms.realestatemanager.data.entity.*
/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
@Database(
        entities = [Address::class, Agent::class, Amenity::class, Picture::class, Property::class],
        version = 1,
        exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RealStateManagerDatabase : RoomDatabase(){

    // --- DAO
    abstract fun addressDao(): AddressDao
    abstract fun agentDao(): AgentDao
    abstract fun amenityDao(): AmenityDao
    abstract fun pictureDao(): PictureDao
    abstract fun propertyDao(): PropertyDao

    companion object{
        @Volatile
        private var INSTANCE: RealStateManagerDatabase? = null
        fun getDatabase(context: Context): RealStateManagerDatabase {
            return INSTANCE
                    ?: synchronized(this){
                        val instance = Room.databaseBuilder(
                                context.applicationContext,
                                RealStateManagerDatabase::class.java,
                                "REM_db.db")
                                .build()
                        INSTANCE = instance
                        return instance
                    }
        }
    }
}
