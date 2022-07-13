package com.xuuxxi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuuxxi.entity.AddressBook;
import com.xuuxxi.mapper.AddressBookMapper;
import com.xuuxxi.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/14
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
