package com.example.ecotrack.data.database.dao

import androidx.room.*
import com.example.ecotrack.domain.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE isLoggedIn = 1 LIMIT 1")
    fun getCurrentUser(): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("UPDATE users SET isLoggedIn = 0")
    suspend fun logoutAll()

    @Query("UPDATE users SET totalPoints = :points, globalStreak = :streak WHERE isLoggedIn = 1")
    suspend fun updateUserStats(points: Int, streak: Int)

    @Query("UPDATE users SET totalPoints = totalPoints - :price WHERE isLoggedIn = 1")
    suspend fun deductPoints(price: Int)

    @Query("SELECT totalPoints FROM users WHERE isLoggedIn = 1")
    suspend fun getCurrentBalance(): Int
}