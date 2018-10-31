package com.micrDemo.course.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.micrDemo.course.provider.dao.CourseDao;
import com.micrDemo.course.provider.thrift.ServiceProvider;
import com.micro.thrift.user.UserInfo;
import com.micro.thrift.user.dto.TeacherDTO;
import com.microDemo.course.api.dto.CourseDTO;
import com.microDemo.course.api.service.ICourseService;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 继承实现Dubbo定义类
 * author: mSun
 * date: 2018/10/30
 */
@Service(
        version = "${course.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private ServiceProvider serviceProvider;

    @Override
    public List<CourseDTO> courseList() {

        List<CourseDTO> courseDTOList = courseDao.listCourse();

        if (courseDTOList!=null) {
            for (CourseDTO courseDTO: courseDTOList) {
                Integer teacherId = courseDao.getCourseTeacher(courseDTO.getId());
                if(teacherId!=null) {
                    try {
                        UserInfo userInfo = serviceProvider.getUserService().getTeacherById(teacherId);
                        TeacherDTO teacherDTO = new TeacherDTO();
                        BeanUtils.copyProperties(userInfo, teacherDTO);
                        courseDTO.setTeacherDTO(teacherDTO);
                    } catch (TException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }
        return courseDTOList;
    }
}
