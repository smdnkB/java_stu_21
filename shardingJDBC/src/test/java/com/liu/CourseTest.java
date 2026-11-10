package com.liu;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liu.entity.Course;
import com.liu.mapper.CourseMapper;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class CourseTest {
    @Resource
    private CourseMapper courseMapper;

    @Test
    public void addCourse(){

        for (int i = 70; i < 80; i++) {
            Course course = new Course();
            course.setUserId(i*1000L);
            course.setCid(i*1000L+ i);
            course.setStatus("0");
            course.setName(i*1000+"");
            courseMapper.insert(course);
        }

    }

    @Test
    public void queryCourse(){
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();

//        courseQueryWrapper.eq("cid",1L);
        List<Course> courses = courseMapper.selectList(courseQueryWrapper);
        courses.forEach(System.out::println);
    }
}
