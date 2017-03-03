/**
*   Created by Igor Kryviuk
*/
<!-- Modal window for confirm deleting-->
<div id="modalDeleteConfirm" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header alert alert-danger" role="alert">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Delete</h4>
            </div>
            <div class="modal-body text-center">
                Do you really want to delete "<b class="productName"></b>" from your <span class="pageName"></span>?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary btn-confirm">Yes</button>
            </div>
        </div>
    </div>
</div>
