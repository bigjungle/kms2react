import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IParaAttrSdmSuffix, defaultValue } from 'app/shared/model/para-attr-sdm-suffix.model';

export const ACTION_TYPES = {
  SEARCH_PARAATTRS: 'paraAttr/SEARCH_PARAATTRS',
  FETCH_PARAATTR_LIST: 'paraAttr/FETCH_PARAATTR_LIST',
  FETCH_PARAATTR: 'paraAttr/FETCH_PARAATTR',
  CREATE_PARAATTR: 'paraAttr/CREATE_PARAATTR',
  UPDATE_PARAATTR: 'paraAttr/UPDATE_PARAATTR',
  DELETE_PARAATTR: 'paraAttr/DELETE_PARAATTR',
  SET_BLOB: 'paraAttr/SET_BLOB',
  RESET: 'paraAttr/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IParaAttrSdmSuffix>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ParaAttrSdmSuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: ParaAttrSdmSuffixState = initialState, action): ParaAttrSdmSuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PARAATTRS):
    case REQUEST(ACTION_TYPES.FETCH_PARAATTR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PARAATTR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PARAATTR):
    case REQUEST(ACTION_TYPES.UPDATE_PARAATTR):
    case REQUEST(ACTION_TYPES.DELETE_PARAATTR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PARAATTRS):
    case FAILURE(ACTION_TYPES.FETCH_PARAATTR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PARAATTR):
    case FAILURE(ACTION_TYPES.CREATE_PARAATTR):
    case FAILURE(ACTION_TYPES.UPDATE_PARAATTR):
    case FAILURE(ACTION_TYPES.DELETE_PARAATTR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PARAATTRS):
    case SUCCESS(ACTION_TYPES.FETCH_PARAATTR_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARAATTR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PARAATTR):
    case SUCCESS(ACTION_TYPES.UPDATE_PARAATTR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PARAATTR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/para-attrs';
const apiSearchUrl = 'api/_search/para-attrs';

// Actions

export const getSearchEntities: ICrudSearchAction<IParaAttrSdmSuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PARAATTRS,
  payload: axios.get<IParaAttrSdmSuffix>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IParaAttrSdmSuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PARAATTR_LIST,
    payload: axios.get<IParaAttrSdmSuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IParaAttrSdmSuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PARAATTR,
    payload: axios.get<IParaAttrSdmSuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IParaAttrSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PARAATTR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IParaAttrSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PARAATTR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IParaAttrSdmSuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PARAATTR,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
