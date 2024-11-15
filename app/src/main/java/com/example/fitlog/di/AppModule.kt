package com.example.fitlog.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fitlog.data.model.FitLogDatabase
import com.example.fitlog.data.model.exercise.repository.ExerciseRepository
import com.example.fitlog.data.model.exercise.repository.ExerciseRepositoryImpl
import com.example.fitlog.ui.add.AddExerciseViewModel
import com.example.fitlog.ui.calendar.CalendarViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::AddExerciseViewModel)
    viewModelOf(::CalendarViewModel)
}

val dataModule = module {
    single {
        Room.databaseBuilder(androidContext(), FitLogDatabase::class.java, "fit-log_database")
            .fallbackToDestructiveMigration()
            .addCallback(roomCallback)
            .build()
    }

    single { get<FitLogDatabase>().exerciseDao() }
}

val repositoryModule = module {
    single<ExerciseRepository> { ExerciseRepositoryImpl(get()) }
}

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

// Spring Server 용
//val dataModule = module {
//    single {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
////            .client(OkHttpClient.Builder().apply {
////                addInterceptor { chain ->
////                    val response = chain.proceed(chain.request())
////                    if (!response.isSuccessful) {
////                        Log.d("Retrofit Interceptor", "fail: ${chain.request().method()} ${chain.request().body()}")
////                    }
////                    response.newBuilder().build()
////                }
////            }.build())
//            .build()
//    }
//    single { get<Retrofit>().create(ExerciseService::class.java) }
//    single { ExerciseRepository(get()) }
//}

val appModules = listOf(
    viewModelModule,
    dataModule,
    repositoryModule
)