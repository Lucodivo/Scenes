package com.inasweaterpoorlyknit.learnopengl_androidport

import kotlin.math.cos
import kotlin.math.sin

import glm_.vec3.Vec3
import glm_.mat4x4.Mat4
import glm_.glm

enum class CameraMovement {
    Forward,
    Backward,
    Left,
    Right,
    Jump
}

const val PITCH = 0.0f
const val YAW = -90.0f
const val SPEED = 2.5f
const val SENSITIVTY = 0.1f
const val ZOOM = 45.0f
const val JUMP_SPEED = 1

class Camera {
    private val position: Vec3 = Vec3(0.0f, 0.0f, 3.0f)
    private var up: Vec3 = Vec3(0.0f, 1.0f, 0.0f)
    private val yaw: Float = YAW
    private val pitch: Float = PITCH

    private var front = Vec3(0.0f, 0.0f, -1.0f)
    private var right = Vec3(1.0f, 0.0f, 0.0f)
    private var worldUp = Vec3(0.0f, 1.0f, 0.0f)

    var movementSpeed = SPEED
    var zoom = ZOOM

    var deltaPosition = Vec3(0.0f, 0.0f, 0.0f)

    init {
        updateCameraVectors()
    }

    // Returns the view matrix calculated using Eular Angles and the LookAt Matrix
    fun getViewMatrix(deltaTime: Float) : Mat4
    {
        return lookAt()
    }

    // TODO: Test that the transpose logic is necessary
    private fun lookAt() : Mat4
    {
        val target = position + front

        // Calculate cameraDirection
        val zAxis = glm.normalize(position - target)
        // Get positive right axis vector
        // TODO: Is the inner normalize of up necessary?
        val xAxis = glm.normalize(glm.cross(glm.normalize(up), zAxis))
        // Calculate camera up vector
        val yAxis = glm.cross(zAxis, xAxis)

        // In glm we access elements as mat[col][row] due to column-major layout
        val translation = Mat4(
            1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f,
            -position.x, -position.y, -position.z, 1.0f)

        val rotation = Mat4(
            xAxis.x, yAxis.x, zAxis.x, 0.0f,
            xAxis.y, yAxis.y, zAxis.y, 0.0f,
            xAxis.z, yAxis.z, zAxis.z, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f)

        // Return lookAt matrix as combination of translation and rotation matrix
        // Remember to read from right to left (first translation then rotation)
        return rotation * translation
    }

    // Calculates the front vector from the Camera's (updated) Eular Angles
    private fun updateCameraVectors() {
        // Calculate the new front vector
        val newFront = Vec3(cos(glm.radians(yaw)) * cos(glm.radians(pitch)),
                            sin(glm.radians(pitch)),
                        sin(glm.radians(yaw)) * cos(glm.radians(pitch)))
        front = glm.normalize(newFront)
        // Also re-calculate the right and Up vector
        right = glm.normalize(glm.cross(front, worldUp))  // Normalize the vectors, because their length gets closer to 0 the more you look up or down which results in slower movement.
        up = glm.normalize(glm.cross(right, front))
    }
}