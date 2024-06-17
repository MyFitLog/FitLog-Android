package com.example.fitlog.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fitlog.data.room.FitLogDatabase
import com.example.fitlog.data.room.exercise.ExerciseRepository
import com.example.fitlog.data.room.exercise.ExerciseRepositoryImpl
import com.example.fitlog.ui.add.AddExerciseViewModel
import com.example.fitlog.ui.calendar.CalendarViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

}

val viewModelModule = module {
    viewModelOf(::AddExerciseViewModel)
    viewModelOf(::CalendarViewModel)
}

val roomModule = module {
    single {
        Room.databaseBuilder(androidContext(), FitLogDatabase::class.java, "fitlog_database")
            .fallbackToDestructiveMigration()
            .addCallback(roomCallback)
            .build()
    }
    single { get<FitLogDatabase>().exerciseDao() }
    single<ExerciseRepository> { ExerciseRepositoryImpl(get()) }
}

val appModules = listOf(
    appModule,
    viewModelModule,
    roomModule
)

val roomCallback = object : RoomDatabase.Callback() {
    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        val cursor = db.query("SELECT COUNT(*) FROM exercise_name")
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()

        if (count == 0) {
            db.execSQL("INSERT INTO exercise_name (name) VALUES('스쿼트')")
            db.execSQL("INSERT INTO exercise_name (name) VALUES('레그 프레스')")
            db.execSQL("INSERT INTO exercise_name (name) VALUES('레그 익스텐션')")
            db.execSQL("INSERT INTO exercise_name VALUES('레그 컬')")
            db.execSQL("INSERT INTO exercise_name VALUES('이너 싸이')")
            db.execSQL("INSERT INTO exercise_name VALUES('컨벤셔널 데드 리프트')")
            db.execSQL("INSERT INTO exercise_name VALUES('루마니안 데드 리프트')")
            db.execSQL("INSERT INTO exercise_name VALUES('벤치 프레스')")
            db.execSQL("INSERT INTO exercise_name VALUES('플라잉 머신')")
            db.execSQL("INSERT INTO exercise_name VALUES('체스트 프레스')")
            db.execSQL("INSERT INTO exercise_name VALUES('렛 풀 다운')")
            db.execSQL("INSERT INTO exercise_name VALUES('티 바 로우')")
            db.execSQL("INSERT INTO exercise_name VALUES('케이블 로우')")
            db.execSQL("INSERT INTO exercise_name VALUES('암 컬')")
            db.execSQL("INSERT INTO exercise_name VALUES('해머 컬')")
            db.execSQL("INSERT INTO exercise_name VALUES('라잉 트라이셉스 익스텐션')")
        }
    }
}