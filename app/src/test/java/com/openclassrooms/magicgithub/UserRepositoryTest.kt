package com.openclassrooms.magicgithub

import com.openclassrooms.magicgithub.di.Injection
import com.openclassrooms.magicgithub.model.User
import com.openclassrooms.magicgithub.repository.UserRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class UserRepositoryTest {
    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        repository = Injection.getRepository()
    }

    @Test
    fun generateRandomUserWithSuccess() {
        val initialSize = repository.getUsers().size
        repository.addRandomUser()
        assertEquals(initialSize + 1, repository.getUsers().size)
    }

    @Test
    fun deleteUserWithSuccess() {
        val user = repository.getUsers().first()
        repository.deleteUser(user)
        assertFalse(repository.getUsers().contains(user))
    }

    @Test
    fun newUserIsActiveByDefault() {
        repository.addRandomUser()
        val lastUser = repository.getUsers().last()
        assertTrue(lastUser.isActive)
    }

    @Test
    fun toggleUserActiveStatus() {
        // Get first user
        val user = repository.getUsers().first()

        // Store initial status
        val initialStatus = user.isActive

        // Toggle status
        user.isActive = !initialStatus

        // Verify status changed
        assertNotEquals(initialStatus, user.isActive)
    }

    @Test
    fun userListOrderingPreserved() {
        // Get initial list
        val initialList = repository.getUsers()

        // Add new user
        repository.addRandomUser()

        // Get updated list
        val updatedList = repository.getUsers()

        // Verify all initial users are in same order
        for (i in initialList.indices) {
            assertEquals(initialList[i].id, updatedList[i].id)
        }
    }
}