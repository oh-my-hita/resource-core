package com.stupidtree.hitax.plugin

import android.app.Application
import com.stupidtree.hita.api.HostEnvironmentService
import com.stupidtree.hita.api.Plugin
import com.stupidtree.hita.api.PluginContext
import com.stupidtree.hita.api.ResourceDataService
import com.stupidtree.hita.api.TeacherService

/** T1 resource core: HOA/teacher data only; no UI or Activities. */
class ResourceCorePlugin : Plugin {
    override val id = "com.stupidtree.hitax.resource"
    override val displayName = "课程资源核心"
    override val requiresApi = 11

    private var application: Application? = null

    override fun install(ctx: PluginContext) {
        val app = (ctx.service(HostEnvironmentService.KEY)?.applicationContext as? Application)
            ?: error("缺少宿主 applicationContext")
        application = app
        ctx.provide(ResourceDataService.KEY, ResourceDataServiceImpl())
        ctx.provide(TeacherService.KEY, TeacherServiceImpl(app))
    }

    override fun purgeData() {
        application?.let { java.io.File(it.filesDir, "plugins/$id").deleteRecursively() }
    }
}
