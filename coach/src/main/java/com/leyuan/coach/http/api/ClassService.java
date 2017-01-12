package com.leyuan.coach.http.api;

import com.leyuan.coach.bean.BaseBean;
import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.bean.CourseResult;
import com.leyuan.coach.bean.MyCalendar;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by user on 2016/12/23.
 */

public interface ClassService {

    //coachId：教练ID type:0（课表，上月课表），1：下月课表
    @GET("course/getCoachCalendar.json")
    Observable<BaseBean<ArrayList<MyCalendar>>> getCalendar(@Query("coachId") String id, @Query("type") String type);

    //coachId：教练ID courseTime：课程时间2017-11-11
    @GET("course/getCoachCourseList.json")
    Observable<BaseBean<CourseResult>> getCourseList(@Query("coachId") String id, @Query("courseTime") String time);


    @GET("course/getReplaceCourseList.json")
    Observable<BaseBean<ArrayList<ClassSchedule>>> getReplaceCourseList(@Query("coachId") String id);

    //coachId：教练ID timetableId 课程ID；status：（1：同意，7拒绝）
    @FormUrlEncoded
    @POST("course/updateCourseById.json")
    Observable<BaseBean<Object>> takeOverClassConfirm(@Field("coachId") String coachId,
                                                      @Field("timetableId") String timetableId, @Field("status") String status);

    @GET("course/getSuspendClassList.json")
    Observable<BaseBean<ArrayList<ClassSchedule>>> getSuspendCourseList(@Query("coachId") String id);

    //coachId：教练ID timetableId 课程ID；status：（1：同意，7拒绝）
    @FormUrlEncoded
    @POST("course/updateSuspendClass.json")
    Observable<BaseBean<Object>> suspendClassConfirm(@Field("coachId") String coachId,
                                                     @Field("timetableId") String timetableId);

    @FormUrlEncoded
    @POST("course/addSignIn.json")
    Observable<BaseBean<Object>> signIn(@Field("coachId") String coachId, @Field("timetableId")
            String timetableId, @Field("lat") String lat, @Field("lng") String lng);

    //coachId：教练ID courseTime：课程时间2017-11-11  type:类型（0待确认，1已确认）
    @GET("course/getNextMonthCourse.json")
    Observable<BaseBean<CourseResult>> getNextMonthCourseList(@Query("coachId") String id,
                                                                       @Query("courseTime") String courseTime, @Query("type") String type);

    @FormUrlEncoded
    @POST("course/updateNextCourseStatus.json")
    Observable<BaseBean<Object>> nextMonthCourseConfirm(@Field("coachId") String coachId, @Field("courseTime") String courseTime);

}
