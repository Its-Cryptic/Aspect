//package dev.cryptic.aspect.blockentities.fluxcore;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import com.mojang.math.Vector3f;
//import net.minecraft.client.renderer.texture.OverlayTexture;
//
//public class SphereRenderer {
//    public static final SphereRenderer L = new SphereRenderer(9, 7);
//    public static final SphereRenderer M = new SphereRenderer(24, 9);
//    public static final SphereRenderer H = new SphereRenderer(32, 16);
//
//    public record Col(int index, float u, float x, float z) {
//    }
//
//    public record Row(int index, float v, float y, float m) {
//    }
//
//    public final Col[] cols;
//    public final Row[] rows;
//    public final Vector3f[][] normals;
//    public int colR = 255;
//    public int colG = 255;
//    public int colB = 255;
//    public int colA = 255;
//
//    public SphereRenderer(int hd, int vd) {
//        this.cols = new Col[hd + 1];
//        this.rows = new Row[vd + 1];
//        this.normals = new Vector3f[hd][vd];
//        calculate();
//    }
//
//    public void calculate() {
//        for (int i = 0; i < cols.length; i++) {
//            double d = i / (cols.length - 1D);
//            this.cols[i] = new Col(i, (float) (1D - d), (float) (Math.cos(d * Math.PI * 2D) * 0.5D), (float) (Math.sin(d * Math.PI * 2D) * 0.5D));
//        }
//
//        for (int i = 1; i < rows.length - 1; i++) {
//            double d = i / (rows.length - 1D);
//            this.rows[i] = new Row(i, (float) d, (float) (Math.cos(d * Math.PI) * 0.5D), (float) (((Math.sin(d * Math.PI) + 1D) / 2D - 0.5D) * 2D));
//        }
//
//        rows[0] = new Row(0, 0F, 0.5F, 0F);
//        rows[rows.length - 1] = new Row(rows.length - 1, 1F, -0.5F, 0F);
//
//        for (int r = 0; r < rows.length - 1; r++) {
//            for (int c = 0; c < cols.length - 1; c++) {
//                var cr = rows[r];
//                var nr = rows[r + 1];
//                var cc = cols[c];
//                var nc = cols[c + 1];
//
//                // var n = new Vector3f(cr.x * nc.m, cr.y, cr.z * nc.m).cross(new Vector3f(nr.x * cc.m, nr.y, nr.z * cc.m)).normalize();
//                normals[c][r] = new Vector3f(0F, 1F, 0F);
//            }
//        }
//
//        for (int r = 0; r < rows.length - 1; r++) {
//            for (int c = 0; c < cols.length - 1; c++) {
//                var cr = rows[r];
//                var nr = rows[r + 1];
//                var cc = cols[c];
//                var nc = cols[c + 1];
//
//                var va = new Vector3f(cc.x * nr.m, nr.y, cc.z * nr.m);
//                var vb = new Vector3f(cc.x * cr.m, cr.y, cc.z * cr.m);
//                var vc = new Vector3f(nc.x * cr.m, cr.y, nc.z * cr.m);
//                var vd = new Vector3f(nc.x * nr.m, nr.y, nc.z * nr.m);
//
//                normals[c][r] = vb.sub(va).mul(vc.sub(vb)).normalize();
//            }
//        }
//    }
//
//    // POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL
//    public void renderEntity(PoseStack poseStack, VertexConsumer layer, int light, int overlay) {
//        var e = poseStack.last();
//        var m = e.pose();
//        var n = e.normal();
//
//        for (int r = 0; r < rows.length - 1; r++) {
//            for (int c = 0; c < cols.length - 1; c++) {
//                var cr = rows[r];
//                var nr = rows[r + 1];
//                var cc = cols[c];
//                var nc = cols[c + 1];
//                var nv = normals[c][r];
//
//                layer.vertex(m, cc.x * nr.m, nr.y, cc.z * nr.m).color(colR, colG, colB, colA).uv(cc.u, nr.v).color(overlay).uv2(light).normal(n, nv.x, nv.y, nv.z).next();
//                layer.vertex(m, cc.x * cr.m, cr.y, cc.z * cr.m).color(colR, colG, colB, colA).texture(cc.u, cr.v).overlay(overlay).light(light).normal(n, nv.x, nv.y, nv.z).next();
//                layer.vertex(m, nc.x * cr.m, cr.y, nc.z * cr.m).color(colR, colG, colB, colA).texture(nc.u, cr.v).overlay(overlay).light(light).normal(n, nv.x, nv.y, nv.z).next();
//                layer.vertex(m, nc.x * nr.m, nr.y, nc.z * nr.m).color(colR, colG, colB, colA).texture(nc.u, nr.v).overlay(overlay).light(light).normal(n, nv.x, nv.y, nv.z).next();
//            }
//        }
//    }
//
//    public void renderEntity(MatrixStack ms, VertexConsumer layer, int light) {
//        renderEntity(ms, layer, light, OverlayTexture.DEFAULT_UV);
//    }
//
//    // POSITION_COLOR_TEXTURE_LIGHT_NORMAL
//    public void renderLit(MatrixStack ms, VertexConsumer layer, int light) {
//        var e = ms.peek();
//        var m = e.getPositionMatrix();
//        var n = e.getNormalMatrix();
//
//        for (int r = 0; r < rows.length - 1; r++) {
//            for (int c = 0; c < cols.length - 1; c++) {
//                var cr = rows[r];
//                var nr = rows[r + 1];
//                var cc = cols[c];
//                var nc = cols[c + 1];
//                var nv = normals[c][r];
//
//                layer.vertex(m, cc.x * nr.m, nr.y, cc.z * nr.m).color(colR, colG, colB, colA).texture(cc.u, nr.v).light(light).normal(n, nv.x, nv.y, nv.z).next();
//                layer.vertex(m, cc.x * cr.m, cr.y, cc.z * cr.m).color(colR, colG, colB, colA).texture(cc.u, cr.v).light(light).normal(n, nv.x, nv.y, nv.z).next();
//                layer.vertex(m, nc.x * cr.m, cr.y, nc.z * cr.m).color(colR, colG, colB, colA).texture(nc.u, cr.v).light(light).normal(n, nv.x, nv.y, nv.z).next();
//                layer.vertex(m, nc.x * nr.m, nr.y, nc.z * nr.m).color(colR, colG, colB, colA).texture(nc.u, nr.v).light(light).normal(n, nv.x, nv.y, nv.z).next();
//            }
//        }
//    }
//}