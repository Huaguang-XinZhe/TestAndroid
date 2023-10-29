package com.huaguang.testandroid.data.repositories

class MainRepository(
    private val eventRepository: EventRepository,
    private val categoryRepository: CategoryRepository,
    private val tagRepository: TagRepository
) {


}
